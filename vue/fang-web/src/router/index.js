import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import SignUp from '../views/SignUp.vue'
import UserInfo from '../views/UserInfo.vue'
import OwnerManagerment from '../views/OwnerManagerment.vue'
import TenantManagerment from '../views/TenantManagerment.vue'
import SaveHouseInfo from '../views/SaveHouseInfo.vue'
import HouseInfo from '../views/HouseInfo.vue'
import FangList from '../views/FangList.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'FangList',
    component: FangList
  },
  {
    path: '/about',
    name: 'About',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/signUp',
    name: 'SignUp',
    component: SignUp
  },
  {
    path: '/userInfo',
    name: 'UserInfo',
    component: UserInfo
  },
  {
    path: '/ownerManagerment',
    name: 'OwnerManagerment',
    component: OwnerManagerment
  },
  {
    path: '/tenantManagerment',
    name: 'TenantManagerment',
    component: TenantManagerment
  },
  {
    path: '/saveHouseInfo',
    name: 'SaveHouseInfo',
    component: SaveHouseInfo
  },
  {
    path: '/houseInfo/:id',
    name: 'HouseInfo',
    component: HouseInfo
  }
]

const router = new VueRouter({
  routes
})

export default router
