const axios = {
  get: url => (
    new Promise((resolve) => {
      const name = url.replace(/.*?q=/g, '').replace(/&appid=.*/g, '');

      resolve({
        city: {
          name,
        },
      });
    })
  ),
};

export default axios;
