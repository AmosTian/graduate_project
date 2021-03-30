// @ts-nocheck
import React from 'react';
import { ApplyPluginsType } from 'E:/idea/graduateProject/front/01-react/node_modules/@umijs/runtime';
import * as umiExports from './umiExports';
import { plugin } from './plugin';

export function getRoutes() {
  const routes = [
  {
    "path": "/",
    "component": require('E:/idea/graduateProject/front/01-react/BasicLayout.js').default,
    "routes": [
      {
        "path": "/test",
        "component": require('E:/idea/graduateProject/front/01-react/src/pages/test').default,
        "exact": true
      },
      {
        "path": "/tabstest",
        "component": require('E:/idea/graduateProject/front/01-react/src/pages/tabstest').default,
        "exact": true
      },
      {
        "path": "/user",
        "routes": [
          {
            "path": "/user/add",
            "component": require('E:/idea/graduateProject/front/01-react/src/pages/user/UserAdd').default,
            "exact": true
          },
          {
            "path": "/user/list",
            "component": require('E:/idea/graduateProject/front/01-react/src/pages/user/UserList').default,
            "exact": true
          }
        ]
      }
    ]
  }
];

  // allow user to extend routes
  plugin.applyPlugins({
    key: 'patchRoutes',
    type: ApplyPluginsType.event,
    args: { routes },
  });

  return routes;
}
