const Cagealicious = (() => {

  const baseURL = 'https://www.placecage.com';

  const types = [
    '',
    '/g',
    '/c',
    '/gif',
  ];

  const sizes = [
    100,
    125,
    150,
    175,
    200,
    225,
    250,
    275,
    300,
  ];

  let count = 0;
  let countElement;

  const add = () => {
    // 1. Get random image url
    const type = types[Math.floor(Math.random() * types.length)];
    const size = sizes[Math.floor(Math.random() * sizes.length)];
    const url = `${baseURL}${type}/${size}/${size}`;

    // 2. Create the image at random position
    const img = document.createElement('img');
    img.className = 'cagealicious-img';
    img.setAttribute('src', url);
    img.setAttribute('alt', 'Nic Cage');
    img.style.top = `${Math.random() * (window.innerHeight - size)}px`;
    img.style.left = `${Math.random() * (window.innerWidth - size)}px`;
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
    countElement.textContent = `${count} Cage${count > 1 ? 's' : ''} created`;
  };

  const replace = () => {
    const imgs = [].slice.call(document.body.querySelectorAll('img'));
    imgs.forEach((img) => {
      const type = types[Math.floor(Math.random() * types.length)];
      const width = img.width;
      const height = img.height;
      const url = `${baseURL}${type}/${width}/${height}`;
      img.setAttribute('src', url);
      img.setAttribute('alt', 'Nic Cage');
    });
  };

  return {
    add,
    replace,
  };

})();