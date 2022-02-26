import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router'
import Home from '../views/Home.vue'
import UserAdmin from "@/views/admin/UserAdmin.vue";
import store from "@/store";
import {Tool} from "@/util/tool";

const routes: Array<RouteRecordRaw> = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/admin/user',
    name: 'UserAdmin',
    component: UserAdmin,
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/doc',
    name: 'Doc',
    component: () => import(/* webpackChunkName: "about" */ '../views/Doc.vue')
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
    path: '/admin/music',
    name: 'MusicAdmin',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/admin/MusicAdmin.vue'),
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/admin/category',
    name: 'CategoryAdmin',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/admin/CategoryAdmin.vue'),
    meta: {
      loginRequire: true
    }
  },
  {
    path: '/admin/doc',
    name: 'DocAdmin',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/admin/DocAdmin.vue'),
    meta: {
      loginRequire: true
    }
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to, from, next) => {
  // 要不要对meta.loginRequire属性做监控拦截
  if (to.matched.some(function (item) {
    console.log(item, "是否需要登录校验：", item.meta.loginRequire);
    return item.meta.loginRequire
  })) {
    const loginUser = store.state.user;
    if (Tool.isEmpty(loginUser)) {
      console.log("用户未登录！");
      next('/');
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router
