(function() {

  var columnId;

  /*
   * Create Column
   */

  $(document).on('submit', '.js-create-column-form', function(e) {
    e.preventDefault();

    var formElement = $(this);

    var createColumnPath = formElement.find('#create_column_path').val();

    $.post(createColumnPath, formElement.serialize())
      .done(function(res) {
        var modalElement = $('#js-create-column-modal');
        dawdle.toggleModal(modalElement);
        dawdle.resetFormElement(formElement);
        addNewColumn(res.column);
      })
      .fail(function(err) {
        var submitElement = formElement.find('.js-submit');
        var errors = err.responseJSON || { error: 'Could not create column. Please try again.' };
        dawdle.renderFormErrors(formElement, errors);
        submitElement.prop('disabled', false);
        submitElement.removeClass('is-loading');
      });
  });

  function addNewColumn(column) {
    var columnElement = $(
      '   <div class="board-column column is-fullheight is-12 js-board-column">  '  +
      '     <div class="board-column-outline has-background-info box is-fullwidth is-fullheight px-4 py-4">  '  +
      '       <div class="columns is-vcentered is-mobile">  '  +
      '         <div class="column is-9">  '  +
      '           <h2 class="title is-6 has-alt-text has-text-white js-shave-sm js-column-name"></h2>  '  +
      '         </div>  '  +
      '         <div class="column is-3">  '  +
      '           <div class="dropdown is-hoverable is-right">  '  +
      '             <div class="dropdown-trigger">  '  +
      '               <button class="button is-info is-inverted" aria-haspopup="true" aria-controls="dropdown-menu" aria-label="dropdown menu">  '  +
      '                 <span class="icon is-small">  '  +
      '                   <i class="fas fa-angle-down" aria-hidden="true"></i>  '  +
      '                 </span>  '  +
      '               </button>  '  +
      '             </div>  '  +
      '             <div class="dropdown-menu" id="dropdown-menu" role="menu">  '  +
      '               <div class="dropdown-content">  '  +
      '                 <a href="#" class="dropdown-item js-modal-trigger js-create-card-modal-trigger" data-target="#js-create-card-modal">  '  +
      '                   Create New Card  '  +
      '                 </a>  '  +
      '                 <hr class="dropdown-divider">  '  +
      '                 <a href="#" class="dropdown-item js-modal-trigger js-update-column-modal-trigger" data-target="#js-update-column-modal">  '  +
      '                   Update Column  '  +
      '                 </a>  '  +
      '                 <a href="#" class="dropdown-item js-modal-trigger js-delete-column-modal-trigger" data-target="#js-delete-column-modal">  '  +
      '                   Delete Column  '  +
      '                 </a>  '  +
      '               </div>  '  +
      '             </div>  '  +
      '           </div>  '  +
      '         </div>  '  +
      '       </div>  '  +
      '       <div class="columns is-mobile is-multiline is-fullheight">  '  +
      '         <div class="column is-12">  '  +
      '           <div class="columns is-mobile is-fullheight is-multiline js-card-container card-container">  '  +
      '           </div>  '  +
      '         </div>  '  +
      '       </div>  '  +
      '     </div>  '  +
      '  </div>  '
    );

    columnElement.attr('data-column-id', column._id.$oid);
    columnElement.attr('data-column-name', column.name);
    columnElement.find('.js-column-name').text(column.name);

    $('.js-create-new-column-container').before(columnElement);
    initSortable();
  }

  $(document).on('click', '.js-create-column-form .js-modal-trigger', function() {
    var formElement = $('.js-create-column-form');
    dawdle.resetFormElement(formElement);
  });

  /*
   * Update Column
   */

  $(document).on('submit', '.js-update-column-form', function(e) {
    e.preventDefault();

    var formElement = $(this);

    var updateColumnPath = '/column/' + columnId;

    $.post(updateColumnPath, formElement.serialize())
      .done(function(res) {
        var modalElement = $('#js-update-column-modal');
        dawdle.toggleModal(modalElement);
        var columnElement = $('[data-column-id=' + res.column._id.$oid + ']');
        columnElement.attr('data-column-id', res.column._id.$oid);
        columnElement.attr('data-column-name', res.column.name);
        var columnNameElement = $(columnElement).find('.js-column-name');
        columnNameElement.text(res.column.name);
        dawdle.truncateText();
        resetUpdateColumnForm(formElement);
      })
      .fail(function(err) {
        var submitElement = formElement.find('.js-submit');
        var errors = err.responseJSON || { error: 'Could not update column. Please try again.' };
        dawdle.renderFormErrors(formElement, errors);
        submitElement.prop('disabled', false);
        submitElement.removeClass('is-loading');
      });
  });

  function resetUpdateColumnForm(formElement) {
    var options = {};
    if (columnId) {
      var columnElement = $('[data-column-id=' + columnId + ']');
      options.state = {
        name: $(columnElement).attr('data-column-name'),
      }
    }

    dawdle.resetFormElement(formElement, options);
  }

  $(document).on('click', '.js-update-column-form .js-modal-trigger', function() {
    var formElement = $('.js-update-column-form');
    resetUpdateColumnForm(formElement);
  });

  $(document).on('click', '.js-update-column-modal-trigger', function() {
    columnId = $(this).parents('.js-board-column').attr('data-column-id');
    var formElement = $('.js-update-column-form');
    resetUpdateColumnForm(formElement);
  });

  /*
   * Delete Column
   */

  $(document).on('submit', '.js-delete-column-form', function(e) {
    e.preventDefault();

    var formElement = $(this);

    var deleteColumnPath = '/column/' + columnId + '/delete';

    $.post(deleteColumnPath, formElement.serialize())
      .done(function(res) {
        var modalElement = $('#js-delete-column-modal');
        dawdle.toggleModal(modalElement);
        dawdle.resetFormElement(formElement);
        removeColumn(res.id);
      })
      .fail(function(err) {
        var submitElement = formElement.find('.js-submit');
        var errors = err.responseJSON || { error: 'Could not delete column. Please try again.' };
        dawdle.renderFormErrors(formElement, errors);
        submitElement.prop('disabled', false);
        submitElement.removeClass('is-loading');
      });
  });

  function removeColumn(columnId) {
    $('[data-column-id=' + columnId+ ']').remove();
  }

  $(document).on('click', '.js-delete-column-form .js-modal-trigger', function() {
    var formElement = $('.js-delete-column-form');
    dawdle.resetFormElement(formElement);
  });

  $(document).on('click', '.js-delete-column-modal-trigger', function() {
    columnId = $(this).parents('.js-board-column').attr('data-column-id');
  });

  /*
   * Sortable Cards
   */

  function initSortable() {
    $('.js-card-container').sortable({
      connectWith: '.js-card-container',
      items: '.js-board-card',
      receive: moveCard,
      update: function(event, ui) {
        if (this === ui.item.parent()[0]) {
          if (ui.sender === null) {
            moveCard(event, ui);
          }
        }
      },
    });
  }

  // TODO move this to card.js file
  function moveCard(event, ui) {
    var card = $(ui.item[0]);
    var cardId = card.attr('data-card-id');
    var prevCard = card.prev('.js-board-card');
    var prevCardId = prevCard.attr('data-card-id');
    var column = $(event.target).parents('.js-board-column');
    var columnId = column.attr('data-column-id');

    var moveCardPath = '/card/' + cardId + '/move';

    var payload = {
      new_column_id: columnId,
      prev_card_id: prevCardId,
    };

    $.ajax({
      type: 'POST',
      contentType: 'application/json',
      url: moveCardPath,
      dataType: 'json',
      data: JSON.stringify(payload),
      error: function() {
        $('.js-card-container').sortable('cancel');
        dawdle.renderNotification('Could not move card. Please try again.', 'danger');
      },
    });
  }

  initSortable();
})();
