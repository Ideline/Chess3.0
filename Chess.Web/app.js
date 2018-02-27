(function () {
  var board = {};
  var baseurl = 'http://localhost:9999';
  var moveHandler;
  var numberOfMoves = 0;
  var paus = false;
  var interval = 1500;
  var check = false;

  $(function (ready) {
    console.info('Spelet startar ...');

    $.getJSON(`${baseurl}/start`, function (json) {
      board = json;
      console.info(`Spelet startat ...`);

      moveHandler = setInterval(getNextMove, 1500);
    }).fail(function () {
      console.warn('Spelet kunde ej startas.');
    });
  });

  $('#pause').on('click', function () {
    paus = true;
    $('.pause').show();
    $('#pause').fadeIn(100).fadeOut(100).fadeIn(100);
    if($('#check').is(":visible")){
      check = true;
      $('#check').hide();
    }
  });

  $('#play').on('click', function () {
    paus = false;
    $('.pause').hide();
    $('#play').fadeIn(100).fadeOut(100).fadeIn(100);
    clearInterval(moveHandler);
    moveHandler = setInterval(getNextMove, 1500);
    if(check === true){
      check = false;
      $('#check').show();
    }
  });

  $('#slower').on('click', function () {
    clearInterval(moveHandler);
    $('#slower').fadeIn(100).fadeOut(100).fadeIn(100);
    moveHandler = setInterval(getNextMove, 3000);
  });

  $('#faster').on('click', function () {
    clearInterval(moveHandler);
    $('#faster').fadeIn(100).fadeOut(100).fadeIn(100);
    moveHandler = setInterval(getNextMove, 300)
  });

  function getNextMove() {
    if (!paus) {
      $.getJSON(`${baseurl}/move`, function (json) {
        handleMove(json);
      }).fail(() => {
        console.warn('Kunde ej utföra move.');
        clearInterval(moveHandler);
      });
    }
  }

  //Gör movet
  function handleMove(move) {
    console.log(move);
    //debugger;

    if (move.to === "M1") {
      $('.checkmate').show();
      clearInterval(moveHandler);
    }
    else if (move.to === "S1") {
      $('#check').show();
    }
    else {

      $('#check').hide();

      if ($(`#${move.from} img`).length === 0)
        return;

      if (move.from === move.to) {
        var src = $(`#${move.from} img`).attr('src');
        $(`#${move.from} img`).remove();
        if (src === "style/img/blackPawn.png") {
          $(`#${move.to}`).append('<img src="style/img/blackQueen.png">');
        }
        else {
          $(`#${move.to}`).append('<img src="style/img/whiteQueen.png">');
        }
      }
      else {

        var posLeftStart = $(`#${move.from}`).css('left');
        var posLeftFinish = $(`#${move.to}`).css('left');;
        var posBottomStart = $(`#${move.from}`).css('bottom');
        var posBottomFinish = $(`#${move.to}`).css('bottom');
        var src = $(`#${move.from} img`).attr('src');

        $(`#${move.from}`).animate({
          bottom: `${posBottomFinish}`,
          left: `${posLeftFinish}`
        }, {
            complete: function () {
              if (move.from === "F1")
                debugger;
              $(`#${move.to} img`).remove();
              $(`#${move.to}`).append(`<img src="${src}">`);
              $(`#${move.from} img`).remove();
              $(`#${move.from}`).css({ "left": posLeftStart, "bottom": posBottomStart });
              console.info('Move utfört:');
            }

          });
      }
    }
    printMoves(move);
  }

  function printMoves(move) {
    numberOfMoves++;
    var chessPiece = $(`#${move.from} img`).attr('src').substr(10);
    chessPiece = getTypeOfChessPiece(chessPiece);

    var message;

    if(move.to === "S1"){
      if(move.from === "A1"){
        chessPiece = `B King`;
      }
      else{
        chessPiece = `W King`;
      }
      message = `<div class="movesContainer"><p class="move">${numberOfMoves} ${chessPiece}:</p><p class="move"> Check!!</p></div>`;
    }
    else{
      message = `<div class="movesContainer"><p class="move">${numberOfMoves} ${chessPiece}:</p><p class="move"> ${move.from} - ${move.to}</p></div>`;
    }

    if (numberOfMoves < 16) {
      $('#moves').prepend(`${message}`);
    }
    else if (numberOfMoves > 15 && numberOfMoves < 21) {
      switch (numberOfMoves) {
        case 16:
          $('#moves').prepend(`${message}`);
          $('#moves div').last().addClass('faded1');
          break;
        case 17:
          $('#moves').prepend(`${message}`);
          $('#moves div').last().addClass('faded2');
          $('#moves div').last().prev().addClass('faded1');
          break;
        case 18:
          $('#moves').prepend(`${message}`);
          $('#moves div').last().addClass('faded3');
          $('#moves div').last().prev().addClass('faded2');
          $('#moves div').last().prev().prev().addClass('faded1');
          break;
        case 19:
          $('#moves').prepend(`${message}`);
          $('#moves div').last().addClass('faded4');
          $('#moves div').last().prev().addClass('faded3');
          $('#moves div').last().prev().prev().addClass('faded2');
          $('#moves div').last().prev().prev().prev().addClass('faded1');
          break;
        case 20:
          $('#moves').prepend(`${message}`);
          $('#moves div').last().addClass('faded5');
          $('#moves div').last().prev().addClass('faded4');
          $('#moves div').last().prev().prev().addClass('faded3');
          $('#moves div').last().prev().prev().prev().addClass('faded2');
          $('#moves div').last().prev().prev().prev().prev().addClass('faded1');
          break;
      }
    }
    else {
      $('#moves').prepend(`<div class="movesContainer"><p class="move">${numberOfMoves} ${chessPiece}:</p><p class="move"> ${move.from} - ${move.to}</p></div>`);
      $('#moves div').last().prev().addClass('faded5');
      $('#moves div').last().prev().prev().addClass('faded4');
      $('#moves div').last().prev().prev().prev().addClass('faded3');
      $('#moves div').last().prev().prev().prev().prev().addClass('faded2');
      $('#moves div').last().prev().prev().prev().prev().prev().addClass('faded1');
      $('#moves div').last().remove();
    }
  }

  function getTypeOfChessPiece(chessPiece) {
    switch (chessPiece) {
      case "whitePawn.png":
        return "W Pawn";
      case "whiteRook.png":
        return "W Rook";
      case "whiteKnight1.png":
        return "W Knight";
      case "whiteKnight2.png":
        return "W Knight";
      case "whiteBishop1.png":
        return "W Bishop";
      case "whiteBishop2.png":
        return "W Bishop";
      case "whiteQueen.png":
        return "W Queen";
      case "whiteKing.png":
        return "W King";
      case "blackPawn.png":
        return "B Pawn";
      case "blackRook.png":
        return "B Rook";
      case "blackKnight1.png":
        return "B Knight";
      case "blackKnight2.png":
        return "B Knight";
      case "blackBishop1.png":
        return "B Bishop";
      case "blackBishop2.png":
        return "B Bishop";
      case "blackQueen.png":
        return "B Queen";
      case "blackKing.png":
        return "B King";
    }
  }

})();