(function() {
  var board = {};
  var baseurl = 'http://localhost:9999';
  var moveHandler;

  $(function(ready) {
    console.info('Spelet startar ...');

    $.getJSON(`${baseurl}/start`, function(json) {
      board = json;
      console.info(`Spelet startat ...`);

      moveHandler = setInterval(getNextMove, 1000);
    }).fail(function() {
      console.warn('Spelet kunde ej startas.');
    });
  });

  function getNextMove() {
    $.getJSON(`${baseurl}/move`, function(json) {
      handleMove(json);
    }).fail(() => {
      console.warn('Kunde ej utföra move.');
      clearInterval(moveHandler);
    });
  }

  //Gör movet
  function handleMove(move) {
    console.log(move);
    //debugger;

    if(move.from === "SS"){
      $('.checkmate').show();
    }


    if($(`#${move.from} img`).length === 0)
      return;

    
    var posLeftStart = $(`#${move.from}`).css('left');
    var posLeftFinish = $(`#${move.to}`).css('left');;
    var posBottomStart = $(`#${move.from}`).css('bottom');
    var posBottomFinish = $(`#${move.to}`).css('bottom');
    var src = $(`#${move.from} img`).attr('src');

    $(`#${move.from}`).animate({ 
      bottom: `${posBottomFinish}`, 
      left: `${posLeftFinish}`
    }, {
      complete: function() {
        if(move.from === "F1")
          debugger;
        $(`#${move.to} img`).remove();
        $(`#${move.to}`).append(`<img src="${src}">`);
        $(`#${move.from} img`).remove();
        $(`#${move.from}`).css({"left": posLeftStart, "bottom": posBottomStart});
        console.info('Move utfört:');
      }
    });
  }

})();