<template>
  <v-app id="customer">
    <div>
      <v-main>
        <v-app-bar></v-app-bar>
        <v-data-table
            :headers="headers"
            :items="customers"
            :options.sync="options"
            :server-items-length="totalCustomers"
            :loading="loading"
            loading-text="Loading... Please wait"
            class="elevation-1"
        >
          <template v-slot:item.valid="{ item }">
            <v-avatar :color="getColor(item.valid)" size="20"></v-avatar>
          </template>
        </v-data-table>
      </v-main>
    </div>
  </v-app>
</template>

<script>
import api from "@/service/apiService";

export default {
  data() {
    return {
      headers: [
        {
          text: 'Name',
          align: 'start',
          sortable: true,
          value: 'name',
        },
        {text: 'Phone', value: 'phone'},
        {text: 'Country', value: 'country'},
        {text: 'Country Code', value: 'countryCode'},
        {text: 'Status', value: 'valid'},
      ],
      totalCustomers: 0,
      loading: true,
      options: {
        "page":1,
        "itemsPerPage":10
      },
      customers: [],
    };
  },
  methods: {
    getCustomers: async function(){
      const requestedData = {
        params: {
          page: this.options.page,
          itemsPerPage: this.options.itemsPerPage
        }
      }
      return await api.getCustomers(requestedData);
    },
    getDataFromApi: function (){
      this.loading = true;
      this.getCustomers().then( data => {
        this.customers = data.content
        this.totalCustomers = data.totalElements
        this.loading = false
      })
    },
    getColor (valid) {
      if (valid) return 'green'
      else return 'red'
    },
  },
  watch: {
    options: {
      handler () {
        this.getDataFromApi()
      },
      deep: true,
    },
  },
}
</script>