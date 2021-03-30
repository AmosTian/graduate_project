import analysis from './zh-CN/analysis';
import exception from './zh-CN/exception';
import form from './zh-CN/form';
import globalHeader from './zh-CN/globalHeader';
import login from './zh-CN/login';
import menu from './zh-CN/menu';
import monitor from './zh-CN/monitor';
import result from './zh-CN/result';
import settingDrawer from './zh-CN/settingDrawer';
import settings from './zh-CN/settings';
import pwa from './zh-CN/pwa';

export default {
  'navBar.lang': '语言',
  'layout.user.link.help': '帮助',
  'layout.user.link.privacy': '隐私',
  'layout.user.link.terms': '条款',
  'app.home.introduce': '介绍',
  'app.forms.basic.title': '基础表单',
  'app.forms.basic.description':
    '表单页用于向用户收集或验证信息，基础表单常见于数据项较少的表单场景。',
  ...analysis,
  ...exception,
  ...form,
  ...globalHeader,
  ...login,
  ...menu,
  ...monitor,
  ...result,
  ...settingDrawer,
  ...settings,
  ...pwa,

  'menu.housingManagement': '房源管理',
  'menu.housingManagement.visit': '看房请求',
  'menu.housingManagement.rent': '租房管理',
  'menu.housingManagement.list': '房源列表',
  'menu.housingManagement.add': '新增房源',

  'menu.landlord': '房东管理',

  'menu.users': '用户管理',

  'menu.contract': '合同管理',

  'menu.news': '资讯管理',

  'menu.qa': '问答管理',

  'menu.finance': '财务管理',
  'menu.finance.bill': '账单管理',
  'menu.finance.withdrawal': '提现审核',

  'menu.system': '系统管理',
  'menu.system.interface': '房源接口',
  'menu.system.dict': '字典配置',
  'menu.system.contract': '合同模板',
};
