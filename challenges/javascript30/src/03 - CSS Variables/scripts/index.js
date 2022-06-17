/*
 * Elements
 */

const inputs = document.querySelectorAll('.js-input');

/*
 * Functions
 */

function handleUpdate() {
  const suffix = this.dataset.sizing || '';
  document.documentElement.style.setProperty(`--${this.name}`, `${this.value}${suffix}`);
}

/*
 * Initialise
 */

inputs.forEach(input => input.addEventListener('change', handleUpdate));
inputs.forEach(input => input.addEventListener('mousemove', handleUpdate));
