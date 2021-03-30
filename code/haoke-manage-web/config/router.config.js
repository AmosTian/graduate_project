export default [
  // user
  {
    path: '/user',
    component: '../layouts/UserLayout',
    routes: [
      { path: '/user', redirect: '/user/login' },
      { path: '/user/login', component: './User/Login' },
      { path: '/user/register', component: './User/Register' },
      { path: '/user/register-result', component: './User/RegisterResult' },
    ],
  },
  // app
  {
    path: '/',
    component: '../layouts/BasicLayout',
    Routes: ['src/pages/Authorized'],
    authority: ['admin', 'user'],
    routes: [

      // housingManagement
      { path: '/', redirect: '/housingManagement/list' },

      //housingManagement
      {
        path: '/housingManagement',
        name: 'housingManagement',
        icon: 'home',
        routes: [
          {
            path: '/housingManagement/list',
            name: 'list',
            component: './haoke/housingManagement/HousingList'
          },
          {
            path: '/housingManagement/add',
            name: 'add',
            component: './haoke/housingManagement/HousingAdd'
          },
          {
            path: '/housingManagement/visit',
            name: 'visit',
            component: './haoke/housingManagement/HousingVisit'
          },
          {
            path: '/housingManagement/rent',
            name: 'rent',
            component: './haoke/housingManagement/HousingRent'
          }
        ]
      },
      // landlord Management
      {
        path: '/landlord',
        name: 'landlord',
        icon: 'key',
        component: './haoke/Landlord'
      },
      // Users Management
      {
        path: '/users',
        name: 'users',
        icon: 'user',
        component: './haoke/users'
      },
      // Contract Management
      {
        path: '/contract',
        name: 'contract',
        icon: 'file-text',
        component: './haoke/contract'
      },
      // News Management
      {
        path: '/news',
        name: 'news',
        icon: 'message',
        component: './haoke/news'
      },
      // QA Management
      {
        path: '/qa',
        name: 'qa',
        icon: 'question-circle',
        component: './haoke/qa'
      },
      //Finance Management
      {
        path: '/finance',
        name: 'finance',
        icon: 'money-collect',
        routes: [
          {
            path: '/finance/bill',
            name: 'bill',
            component: './haoke/Finance/Bill'
          },
          {
            path: '/finance/withdrawal',
            name: 'withdrawal',
            component: './haoke/Finance/withdrawal'
          }
        ]
      },
      //系统管理
      {
        path: '/system',
        name: 'system',
        icon: 'laptop',
        routes: [
          {
            path: '/system/interface',
            name: 'interface',
            component: './haoke/System/Interface'
          },
          {
            path: '/system/dict',
            name: 'dict',
            component: './haoke/System/Dict'
          },
          {
            path: '/system/contract',
            name: 'contract',
            component: './haoke/System/Contract'
          }
        ]
      },
      {
        component: '404',
      },
    ],
  },
];
