<template>
  <v-container>
    <v-card dark color = primary width="400" class="mx-auto mt-5">
      <v-card-title>
        <h1 class="display-1">Login</h1>
      </v-card-title>
      <v-card-text>
        <v-form>
          <v-alert
            color="red"
            type="error"
            v-if="invalidCredentials"
          >
          Invalid Credentials
          </v-alert>
          <v-text-field prepend-icon="mdi-account-circle"  v-model="user.username" label="User"/>
          <v-text-field 
            prepend-icon="mdi-lock" 
            label="Password"
            v-model="user.password"
            :type="showPassword ? 'test' : 'password'" 
            :append-icon="showPassword ? 'mdi-eye':'mdi-eye-off'"
            @click:append="showPassword=!showPassword"
            />
        </v-form>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
          <v-btn color="success" :to="{ name: 'register' }">Register</v-btn>
          
          <v-btn color="info" @click="login">Login</v-btn>
      </v-card-actions>
    </v-card>
  </v-container>

</template>

<script>
import authService from "../services/AuthService";

export default {
  name: "login",
  components: {},
  data() {
    return {
      showPassword : false,
      user: {
        username: "",
        password: ""
      },
      invalidCredentials: false
    };
  },
  methods: {
    login() {
      authService
        .login(this.user)
        .then(response => {
          if (response.status == 200) {
            this.$store.commit("SET_AUTH_TOKEN", response.data.token);
            this.$store.commit("SET_USER", response.data.user);
            this.$router.push("/");
          }
        })
        .catch(error => {
          const response = error.response;

          if (response.status === 401) {
            this.invalidCredentials = true;
          }
        });
    }
  }
};
</script>

<style scoped>

</style>