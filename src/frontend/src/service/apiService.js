import axios from "axios";

const apiClient = {
  async getCustomers(requestData) {
    const response = await axios.get("/app/customers",requestData);
    return response.data;
  },
  async getCountries(){
    const response = await axios.get("/app/Countries");
    return response.data;
  },
};
export default apiClient;
