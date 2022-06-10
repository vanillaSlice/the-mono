(function() {

  /*
   * Navbar
   */

  function toggleNavbar(navbarElement) {
    var navbarBurgerElement = navbarElement.find('.js-navbar-burger');
    var navbarMenuElement = navbarElement.find('.js-navbar-menu');
    var isActive = navbarBurgerElement.hasClass('is-active');
    navbarBurgerElement.toggleClass('is-active', !isActive);
    navbarBurgerElement.attr('aria-expanded', !isActive);
    navbarMenuElement.toggleClass('is-active', !isActive);
  }

  $(document).on('click', '.js-navbar-burger', function() {
    toggleNavbar($(this).parents('.js-navbar'));
  });

  /*
   * Notifications
   */

  $(document).on('click', '.js-notification-close', function() {
    $(this).parent().addClass('is-hidden');
  });

  function renderNotification(message, category) {
    var notificationElement = $('.js-notification');
    var textElement = notificationElement.find('.js-notification-text');
    notificationElement.removeClass('is-' + notificationElement.data('category'));
    notificationElement.addClass('is-' + category);
    textElement.html(message);
    notificationElement.removeClass('is-hidden');
    notificationElement.data('category', category);
  }

  function resetNotification() {
    var notificationElement = $('.js-notification');
    var textElement = notificationElement.find('.js-notification-text');
    textElement.html('');
    notificationElement.addClass('is-hidden');
  }

  /*
   * Forms
   */

  $(document).on('submit', '.js-form', function() {
    var submitElement = $(this).find('.js-submit');
    submitElement.prop('disabled', true);
    submitElement.addClass('is-loading');
  });

  function toggleFieldElementErrorClass(fieldElement, state) {
    if (!state && !fieldElement[0].validity.valid) {
      return;
    }

    var formFieldContainerElement = fieldElement.parents('.js-form-field-container');
    var helpElement = formFieldContainerElement.find('.js-help');
    var passwordMaskElement = formFieldContainerElement.find('.js-password-mask');
    fieldElement.toggleClass('is-danger', state);
    helpElement.toggleClass('is-danger', state);
    passwordMaskElement.toggleClass('is-danger', state);
  }

  $('.js-form-field').on('input', function() {
    toggleFieldElementErrorClass($(this), false);
  });

  $('.js-form-field').on('invalid', function() {
    toggleFieldElementErrorClass($(this), true);
  });

  function togglePasswordMask(buttonElement) {
    var formFieldContainerElement = buttonElement.parents('.js-form-field-container');
    var fieldElement = formFieldContainerElement.find('.js-form-field');
    var iconElement = formFieldContainerElement.find('.js-password-mask-icon');
    if (fieldElement.prop('type') === 'password') {
      fieldElement.prop('type', 'text');
      iconElement.removeClass('fa-eye');
      iconElement.addClass('fa-eye-slash');
    } else {
      fieldElement.prop('type', 'password');
      iconElement.removeClass('fa-eye-slash');
      iconElement.addClass('fa-eye');
    }
  }

  $(document).on('click', '.js-password-mask', function() {
    togglePasswordMask($(this));
  });

  function renderFormErrors(formElement, errors) {
    var errorsElement = formElement.find('.js-form-errors');
    var errorsListElement = errorsElement.find('.js-form-errors-list');

    var errorsList = '';

    Object.keys(errors).forEach(function(key) {
      var fieldElement = formElement.find('#' + key);
      var formFieldContainerElement = fieldElement.parents('.js-form-field-container');
      var helpElement = formFieldContainerElement.find('.js-help');
      var passwordMaskElement = formFieldContainerElement.find('.js-password-mask');
      fieldElement.addClass('is-danger');
      helpElement.addClass('is-danger');
      passwordMaskElement.addClass('is-danger');
      errorsList += '<li class="has-text-weight-bold">' + errors[key] + '</li>';
    });

    errorsListElement.html(errorsList);
    errorsElement.removeClass('is-hidden');
  }

  function resetFormElement(formElement, options) {
    var errorsElement = formElement.find('.js-form-errors');
    var errorsListElement = errorsElement.find('.js-form-errors-list');
    var fieldElements = formElement.find('.js-form-field');
    var helpElements = formElement.find('.js-help');
    var passwordMaskElements = formElement.find('.js-password-mask');
    var submitElement = formElement.find('.js-submit');

    formElement.trigger('reset');

    if (options && options.state) {
      Object.keys(options.state).forEach(function(key) {
        var fieldElement = formElement.find('#' + key);
        fieldElement.val(options.state[key]);
      });
    }

    fieldElements.removeClass('is-danger');
    helpElements.removeClass('is-danger');
    passwordMaskElements.removeClass('is-danger');
    submitElement.prop('disabled', false);
    submitElement.removeClass('is-loading');

    errorsListElement.html('');
    errorsElement.addClass('is-hidden');
  }

  /*
   * Truncation
   */

  function truncateText() {
    $('.js-shave-lg').shave(75, { spaces: false });
    $('.js-shave, .js-shave-md').shave(50, { spaces: false });
    $('.js-shave-sm').shave(25, { spaces: false });
  }

  truncateText();

  $(window).on('resize', _.debounce(truncateText, 250, { leading: true }));

  /*
   * Modals
   */

  function toggleModal(modalElement) {
    modalElement.toggleClass('is-active');
    $('html').toggleClass('is-clipped');
  }

  $(document).on('click', '.js-modal-trigger', function(e) {
    e.preventDefault();

    var triggerElement = $(this);
    var target = triggerElement.data('target');
    var modalElement = target ? $(target) : triggerElement.parents('.js-modal');
    triggerElement.blur();
    toggleModal(modalElement);
  });

  /*
   * Quickviews
   */

  bulmaQuickview.attach();

  /*
   * Used to reload previous page when going back on Safari
   */

  $(window).bind('pageshow', function(event) {
    if (event.originalEvent.persisted) {
      window.location.reload();
    }
  });

  /*
   * Export functions
   */

  window.dawdle = {
    renderFormErrors: renderFormErrors,
    renderNotification: renderNotification,
    resetFormElement: resetFormElement,
    resetNotification: resetNotification,
    toggleFieldElementErrorClass: toggleFieldElementErrorClass,
    toggleModal: toggleModal,
    toggleNavbar: toggleNavbar,
    togglePasswordMask: togglePasswordMask,
    truncateText: truncateText,
  };
})();
