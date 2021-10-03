<template>
  <v-app id="customer">
    <div>
      <v-card>
        <v-card-title>
          <v-toolbar-title>Country Filter</v-toolbar-title>
          <v-select
              outlined
              dense
              clearable
              v-model="selectedCountry"
              :items ="countryItems"
              label="Select Country"
              @input="getDataFromApi"
              flat
              hide-no-data
              hide-details
              class="mx-4"
          ></v-select>
          <v-toolbar-title>Validity Filter</v-toolbar-title>
          <v-select
              outlined
              dense
              clearable
              v-model="selectedValidity"
              :items ="validityItems"
              label="Select validity"
              @input="getDataFromApi"
              flat
              hide-no-data
              hide-details
              class="mx-4"
          ></v-select>
        </v-card-title>
        <v-card-text>
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
        </v-card-text>
      </v-card>
    </div>
  </v-app>
</template>

<script>
import api from "@/service/apiService";

const ValidityStatus = Object.freeze({
  VALID: true,
  INVALID: false
});

export default {
  data() {
    return {
      headers: [
        {text: 'Name', align: 'start', value: 'name',filterable: true,},
        {text: 'Phone', value: 'phone'},
        {text: 'Country', value: 'country', sortable: false,filterable: true,},
        {text: 'Country Code', value: 'countryCode', sortable: false},
        {text: 'Status', value: 'valid',sortable: false},
      ],
      totalCustomers: 0,
      loading: true,
      options: {
        "page":1,
        "itemsPerPage":10,
        "sortBy": ["name"],
        "sortDesc": [false],
      },
      customers: [],
      countryItems:[],
      validityItems:Object.keys(ValidityStatus),
      selectedCountry:"",
      selectedValidity: null,
    };
  },
  methods: {
    getCustomers: async function(requestedData){
      return await api.getCustomers(requestedData);
    },
    getDataFromApi: function (){
      this.loading = true;
      const requestedData = {
        params: {
          page: this.options.page,
          itemsPerPage: this.options.itemsPerPage,
          sortBy:this.options.sortBy[0],
          sortDesc: this.options.sortDesc[0],
          countryName: this.selectedCountry,
          validity:ValidityStatus[this.selectedValidity],
        }
      }
      this.getCustomers(requestedData).then( data => {
        this.customers = data.content;
        this.totalCustomers = data.totalElements;
      }).finally(() => {
        this.loading = false;
      })
    },
    getColor(valid) {
      if (valid) return 'green'
      else return 'red'
    },
    getCountryNames(){
      api.getCountries().then( data => {
        this.countryItems = data;
      })
    },
  },
  watch: {
    options: {
      handler () {
        this.getDataFromApi();
        this.getCountryNames();
      },
      deep: true,
    },
  },
}
</script>