const interceptor = {
  async request(url, options) {
    const token = localStorage.getItem("token");
    if (token) {
      if (!options.headers) {
        options.headers = {};
      }
      options.headers.Authorization = `Bearer ${token}`;
    }
    return [url, options];
  },
  response(response) {
    return response;
  },
};

async function fetchWithInterceptor(url, options) {
  const [newUrl, newOptions] = await interceptor.request(url, options);
  const response = await fetch(newUrl, newOptions);
  return interceptor.response(response);
}

export { fetchWithInterceptor };
