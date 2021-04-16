import Vue from 'vue'
import Router from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import UserCollections from '../views/UserCollections'
import UserHome from '../views/UserHome'
import AddACollection from '../views/AddACollection'
import SearchedUser from '../views/SearchedUser'
import FriendsList from '../views/FriendsList'

import CollectionSearch from '../views/CollectionSearch'

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    { path: '/', redirect: '/home' },
    {
      path: '/home/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/:username/Collections",
      name: "userCollections",
      component: UserCollections,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/account/:username/",
      name: "userHome",
      component: UserHome,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/:username/Collections/addACollection",
      name: "AddACollection",
      component: AddACollection,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/:username/ComicSearch/",
      name:"ComicSearch",
      component: SearchedUser,
      meta: {
        requiresAuth: true
      }
    }
    ,
    {
      path: "/collections/",
      name:"CollectionSearch",
      component: CollectionSearch,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/account/:username/friends",
      name: "friends",
      component: FriendsList,
      meta: {
        requiresAuth: true
      }
    }
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
