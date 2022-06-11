(function() {

  var columnId;
  var cardId;

  /*
   * Create Card
   */

  $(document).on('submit', '.js-create-card-form', function(e) {
    e.preventDefault();

    var formElement = $(this);

    var createCardPath = '/card/?column_id=' + columnId;

    $.post(createCardPath, formElement.serialize())
      .done(function(res) {
        var modalElement = $('#js-create-card-modal');
        dawdle.toggleModal(modalElement);
        dawdle.resetFormElement(formElement);
        addNewCard(res.card);
      })
      .fail(function(err) {
        var submitElement = formElement.find('.js-submit');
        var errors = err.responseJSON || { error: 'Could not create card. Please try again.' };
        dawdle.renderFormErrors(formElement, errors);
        submitElement.prop('disabled', false);
        submitElement.removeClass('is-loading');
      });
  });

  function addNewCard(card) {
    var columnElement = $('[data-column-id=' + card.column_id.$oid + ']');

    var cardElement = $(
      '   <div class="js-board-card board-card column is-12" data-card-id="' + card._id.$oid + '">  '  +
      '     <div class="box px-4 py-4">  '  +
      '       <div class="columns is-mobile">  '  +
      '         <div class="column is-9">  '  +
      '           <h2 class="has-alt-text has-text-weight-bold js-card-name"></h2>  '  +
      '         </div>  '  +
      '         <div class="column is-3">  '  +
      '           <div class="dropdown is-hoverable is-right is-pulled-right">  '  +
      '             <div class="dropdown-trigger">  '  +
      '               <a class="has-text-info" aria-haspopup="true" aria-controls="dropdown-menu" aria-label="dropdown menu">  '  +
      '                 <span class="icon is-small">  '  +
      '                   <i class="fas fa-angle-down" aria-hidden="true"></i>  '  +
      '                 </span>  '  +
      '               </a>  '  +
      '             </div>  '  +
      '             <div class="dropdown-menu" id="dropdown-menu" role="menu">  '  +
      '               <div class="dropdown-content">  '  +
      '                 <a href="#" class="dropdown-item js-modal-trigger js-update-card-modal-trigger" data-target="#js-update-card-modal">  '  +
      '                   Update Card  '  +
      '                 </a>  '  +
      '                 <hr class="dropdown-divider">  '  +
      '                 <a href="#" class="dropdown-item js-modal-trigger js-delete-card-modal-trigger" data-target="#js-delete-card-modal">  '  +
      '                   Delete Card  '  +
      '                 </a>  '  +
      '               </div>  '  +
      '             </div>  '  +
      '           </div>  '  +
      '         </div>  '  +
      '       </div>  '  +
      '     </div>  '  +
      '   </div>  '
    );

    cardElement.attr('data-card-name', card.name);
    cardElement.find('.js-card-name').text(card.name);

    columnElement.find('.js-card-container').append(cardElement);
  }

  $(document).on('click', '.js-create-card-form .js-modal-trigger', function() {
    var formElement = $('.js-create-card-form');
    dawdle.resetFormElement(formElement);
  });

  $(document).on('click', '.js-create-card-modal-trigger', function() {
    columnId = $(this).parents('.js-board-column').attr('data-column-id');
  });

  /*
   * Update Card
   */

  $(document).on('submit', '.js-update-card-form', function(e) {
    e.preventDefault();

    var formElement = $(this);

    var updateCardPath = '/card/' + cardId;

    $.post(updateCardPath, formElement.serialize())
      .done(function(res) {
        var modalElement = $('#js-update-card-modal');
        dawdle.toggleModal(modalElement);
        var cardElement = $('[data-card-id=' + res.card._id.$oid + ']');
        cardElement.attr('data-card-id', res.card._id.$oid);
        cardElement.attr('data-card-name', res.card.name);
        var cardNameElement = $(cardElement).find('.js-card-name');
        cardNameElement.text(res.card.name);
        resetUpdateCardForm(formElement);
      })
      .fail(function(err) {
        var submitElement = formElement.find('.js-submit');
        var errors = err.responseJSON || { error: 'Could not update card. Please try again.' };
        dawdle.renderFormErrors(formElement, errors);
        submitElement.prop('disabled', false);
        submitElement.removeClass('is-loading');
      });
  });

  function resetUpdateCardForm(formElement) {
    var options = {};
    if (cardId) {
      var cardElement = $('[data-card-id=' + cardId + ']');
      options.state = {
        name: $(cardElement).attr('data-card-name'),
      }
    }

    dawdle.resetFormElement(formElement, options);
  }

  $(document).on('click', '.js-update-card-form .js-modal-trigger', function() {
    var formElement = $('.js-update-card-form');
    resetUpdateCardForm(formElement);
  });

  $(document).on('click', '.js-update-card-modal-trigger', function() {
    cardId = $(this).parents('.js-board-card').attr('data-card-id');
    var formElement = $('.js-update-card-form');
    resetUpdateCardForm(formElement);
  });

  /*
   * Delete Card
   */

  $(document).on('submit', '.js-delete-card-form', function(e) {
    e.preventDefault();

    var formElement = $(this);

    var deleteCardPath = '/card/' + cardId + '/delete';

    $.post(deleteCardPath, formElement.serialize())
      .done(function(res) {
        var modalElement = $('#js-delete-card-modal');
        dawdle.toggleModal(modalElement);
        dawdle.resetFormElement(formElement);
        removeCard(res.id);
      })
      .fail(function(err) {
        var submitElement = formElement.find('.js-submit');
        var errors = err.responseJSON || { error: 'Could not delete card. Please try again.' };
        dawdle.renderFormErrors(formElement, errors);
        submitElement.prop('disabled', false);
        submitElement.removeClass('is-loading');
      });
  });

  function removeCard(cardId) {
    $('[data-card-id=' + cardId+ ']').remove();
  }

  $(document).on('click', '.js-delete-card-form .js-modal-trigger', function() {
    var formElement = $('.js-delete-card-form');
    dawdle.resetFormElement(formElement);
  });

  $(document).on('click', '.js-delete-card-modal-trigger', function() {
    cardId = $(this).parents('.js-board-card').attr('data-card-id');
  });
})();
