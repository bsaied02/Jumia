import axios from "axios";

const apiClient = {
  async getCustomers(requestData) {
    const response = await axios.get("/sortedcustomers",requestData);
    return response.data;
  }
};
export default apiClient;
