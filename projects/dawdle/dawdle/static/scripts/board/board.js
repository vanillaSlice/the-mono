(function() {

  var board;

  /*
   * Update Board
   */

  $(document).on('submit', '.js-update-board-form', function(e) {
    e.preventDefault();

    var formElement = $(this);

    var updateBoardPath = formElement.find('#update_board_path').val();

    $.post(updateBoardPath, formElement.serialize())
      .done(function(res) {
        dawdle.renderNotification(res.flash.message, res.flash.category);
        var boardNameElements = $('.js-board-name');
        boardNameElements.text(res.board.name);
        dawdle.truncateText();
        board = res.board;
        resetUpdateBoardForm(formElement);
      })
      .fail(function(err) {
        var submitElement = formElement.find('.js-submit');
        var errors = err.responseJSON || { error: 'Could not update board. Please try again.' }
        dawdle.resetNotification();
        dawdle.renderFormErrors(formElement, errors);
        submitElement.prop('disabled', false);
        submitElement.removeClass('is-loading');
      });
  });

  function resetUpdateBoardForm(formElement) {
    var options = {};
    if (board) {
      options.state = {
        name: board.name,
        owner: board.owner_id.$oid,
        visibility: board.visibility,
      }
    }

    dawdle.resetFormElement(formElement, options);
  }

  $(document).on('click', '.js-board-settings-quickview .js-quickview-close', function() {
    var formElement = $('.js-update-board-form');
    resetUpdateBoardForm(formElement);
  });

  /*
   * Delete Board
   */

  $('.js-delete-board-form').submit(function(e) {
    e.preventDefault();

    var formElement = $(this);

    var deleteBoardPath = formElement.find('#delete_board_path').val();

    $.post(deleteBoardPath, formElement.serialize())
      .done(function(res) {
        var modalElement = $('#js-delete-board-modal');
        dawdle.toggleModal(modalElement);
        dawdle.resetFormElement(formElement);
        window.location = res.url;
      })
      .fail(function(err) {
        var submitElement = formElement.find('.js-submit');
        var errors = err.responseJSON || { error: 'Could not delete board. Please try again.' }
        dawdle.renderFormErrors(formElement, errors);
        submitElement.prop('disabled', false);
        submitElement.removeClass('is-loading');
      });
  });

  $('.js-delete-board-form .js-modal-trigger').click(function() {
    var formElement = $('.js-delete-board-form');
    dawdle.resetFormElement(formElement);
  });
})();
