(function() {

  $(document).on('submit', '.js-create-board-form', function(e) {
    e.preventDefault();

    var formElement = $(this);

    $.post('/board/', formElement.serialize())
      .done(function(res) {
        var modalElement = $('#js-create-board-modal');
        dawdle.toggleModal(modalElement);
        dawdle.resetFormElement(formElement);
        window.location = res.url;
      })
      .fail(function(err) {
        var submitElement = formElement.find('.js-submit');
        var errors = err.responseJSON || { error: 'Could not create board. Please try again.' }
        dawdle.renderFormErrors(formElement, errors);
        submitElement.prop('disabled', false);
        submitElement.removeClass('is-loading');
      });
  });

  $(document).on('click', '.js-create-board-form .js-modal-trigger', function() {
    var formElement = $('.js-create-board-form');
    dawdle.resetFormElement(formElement);
  });
})();
