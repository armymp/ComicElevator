<template>
  <v-container>
    <v-card dark color= primary width="800" class="mx-auto mt-5">
      <v-card-title>
        <h1 class="display-1">Register</h1>
      </v-card-title>
      <v-card-text>
        <v-form>

          <v-alert
            color="red"
            type="error"
            v-if="registrationErrors"
          >
          {{registrationErrorMsg}}
          </v-alert>
          <v-text-field prepend-icon="mdi-account-circle"  v-model="user.username" label="User"/>
          <v-text-field 
            type="password"
            label="Password"
            v-model="user.password"
            prepend-icon="mdi-lock"
            @click:append="showPassword=!showPassword"
            />
            <v-text-field 
            prepend-icon="mdi-lock-check"
            label="confirm Password"
            v-model="user.confirmPassword"
            type="password"
            @click:append="showPassword=!showPassword"
            />

              <v-row>
            <v-col cols=6>
              <v-card height="200" class = "ma-1" :elevation="user.role=='premium' ? 10:2" :color="user.role=='premium' ? 'indigo' : 'primary'" lighten3 @click= "user.role='premium'">
                <v-card-title>
                  Comic Elevator Super!
                </v-card-title>
                <v-card-text>
                  Power up your collection with with a Comic Elevator Super account. Store unlimited amounts of comics in your collections!
                </v-card-text>
              </v-card>
              </v-col>
                <v-col cols=6>
              <v-card  height="200" class = "ma-1" :elevation="user.role=='standard' ? 10:2" :color="user.role=='standard' ? 'indigo' : 'primary'" @click= "user.role='standard'" >
                <v-card-title>
                  Comic Elevator Basic.
                </v-card-title>
                <v-card-text>
                  <v-card-text>
                  A basic Comic Elevator account, you are limited to only 100 comics within your collection.
                  </v-card-text>
                </v-card-text>
              </v-card>
              </v-col>
              </v-row>

        </v-form>
      </v-card-text>
      <v-divider></v-divider>
      <v-card-actions>
        <v-spacer></v-spacer>
          
          <v-btn color="info" @click="register">Register</v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
<!--<div class="card text-center mx-auto bg-light" style="max-width: 650px; margin: 175px 0px" >
  <div class="card-body">
  <div id="register" class="text-center">
    <form class="form-register" @submit.prevent="register">
      <h1 class="h3 mb-3 font-weight-normal">Create Account</h1>
      <div class="alert alert-danger" role="alert" v-if="registrationErrors">
        {{ registrationErrorMsg }}
      </div>
      <div>
      <label for="username" class="sr-only">Username:</label>
      <input
        type="text"
        id="username"
        class="form-control"
        placeholder="Username"
        v-model="user.username"
        required
        autofocus
      />
      </div>
      <label for="password" class="sr-only">Password:</label>
      <input
        type="password"
        id="password"
        class="form-control"
        placeholder="Password"
        v-model="user.password"
        required
      />
      <input
        type="password"
        id="confirmPassword"
        class="form-control"
        placeholder="Confirm Password"
        v-model="user.confirmPassword"
        required
      />
      <h3 style="margin-top: 50px">Choose your account type</h3>
    <div id="cards_landscape_wrap-2">
        <div class="container">
            <div class="row">
                <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
                  <div class="card">

        <div class="image-box">
        <img class="card-img-top" src="../images/captain_america_running.png" alt="Card image cap">
        </div>
          <div class="card-body">
            <h5 class="card-title">Standard User</h5>
            <p class="card-text">Sign up for free today! All standard users can save up to 100 comics per collection.</p>
            <button type="button" class="btn btn-primary" v-on:click="user.role = 'standard'">{{user.role === 'standard' ? 'Selected' : 'Become A Standard User'}}</button>
          </div>
          </div>
      </div>
      <div class="col-xs-12 col-sm-6 col-md-6 col-lg-6">
      <div class="card">
        <div class="image-box">
        <img class="card-img-top" src="../images/ironman_flying.png" alt="Card image cap">
         </div>
          <div class="card-body">
            <h5 class="card-title">Premium User</h5>
            <p class="card-text">Receive all the benefits of a standard account, without a limit on your comic book collections.</p>
            <button type="button" class="btn btn-primary" v-on:click="user.role = 'premium'">{{user.role === 'premium' ? 'Selected' : 'Become A Premium User'}}</button>
          </div>
      </div>
      </div>
            </div>
        </div>
    </div>
      <router-link :to="{ name: 'login' }">Have an account?</router-link>
      <button class="btn btn-lg btn-primary btn-block" type="submit">
        Create My Account
      </button>
    </form>
  </div>
  </div>
  </div> -->
</template>

<script>
import authService from '../services/AuthService';

export default {
  name: 'register',
  data() {
    return {
      user: {
        username: '',
        password: '',
        confirmPassword: '',
        role: 'standard'
      },
      registrationErrors: false,
      registrationErrorMsg: 'There were problems registering this user.',
    };
  },
  methods: {
    register() {
      if (this.user.password != this.user.confirmPassword) {
        this.registrationErrors = true;
        this.registrationErrorMsg = 'Passwords do not match!';
      } else {
        authService
          .register(this.user)
          .then((response) => {
            if (response.status == 201) {
              this.$router.push({
                path: '/login',
                query: { registration: 'success' },
              });
            }
          })
          .catch((error) => {
            const response = error.response;
            this.registrationErrors = true;
            if (response.status === 400) {
              this.registrationErrorMsg = 'Bad Request: Validation Errors';
            }
          });
      }
    },
    clearErrors() {
      this.registrationErrors = false;
      this.registrationErrorMsg = 'There were problems registering this user.';
    },
  },
};
</script>

<style>


</style>
