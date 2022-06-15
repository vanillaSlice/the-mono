window.addEventListener('load', () => {
  const monthNames = [
    'Jan',
    'Feb',
    'Mar',
    'Apr',
    'May',
    'Jun',
    'Jul',
    'Aug',
    'Sep',
    'Oct',
    'Nov',
    'Dec'
  ];

  const monthElement = document.querySelector('.month');
  const dayElement = document.querySelector('.day');
  const date = new Date();

  monthElement.textContent = monthNames[date.getMonth()];
  dayElement.textContent = date.getDate();
});
