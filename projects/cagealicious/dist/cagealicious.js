'use strict';

var Cagealicious = function () {

  var baseURL = 'https://www.placecage.com';

  var types = ['', '/g', '/c', '/gif'];

  var sizes = [100, 125, 150, 175, 200, 225, 250, 275, 300];

  var count = 0;
  var countElement = void 0;

  var add = function add() {
    // 1. Get random image url
    var type = types[Math.floor(Math.random() * types.length)];
    var size = sizes[Math.floor(Math.random() * sizes.length)];
    var url = '' + baseURL + type + '/' + size + '/' + size;

    // 2. Create the image at random position
    var img = document.createElement('img');
    img.className = 'cagealicious-img';
    img.setAttribute('src', url);
    img.setAttribute('alt', 'Nic Cage');
    img.style.top = Math.random() * (window.innerHeight - size) + 'px';
    img.style.left = Math.random() * (window.innerWidth - size) + 'px';
    img.onclick = add;
    document.body.appendChild(img);

    // 3. Create count element if one does not already exist
    if (!countElement) {
      countElement = document.createElement('p');
      countElement.className = 'cagealicious-count';
      document.body.appendChild(countElement);
    }

    // 4. Update count and count element
    count++;
    countElement.textContent = count + ' Cage' + (count > 1 ? 's' : '') + ' created';
  };

  var replace = function replace() {
    var imgs = [].slice.call(document.body.querySelectorAll('img'));
    imgs.forEach(function (img) {
      var type = types[Math.floor(Math.random() * types.length)];
      var width = img.width;
      var height = img.height;
      var url = '' + baseURL + type + '/' + width + '/' + height;
      img.setAttribute('src', url);
      img.setAttribute('alt', 'Nic Cage');
    });
  };

  return {
    add: add,
    replace: replace
  };
}();