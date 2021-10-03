import axios from 'axios'

export default function setup() {
  axios.interceptors.request.use(function (config) {
    return config;
  }, function (error) {
       return Promise.reject(error);
  });

  axios.interceptors.response.use(function (config) {
    return config;
  }, error => {
    console.log("Error: " + error.message);
    return Promise.reject(error);
  });
}
