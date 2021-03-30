import analysis from './zh-TW/analysis';
import exception from './zh-TW/exception';
import form from './zh-TW/form';
import globalHeader from './zh-TW/globalHeader';
import login from './zh-TW/login';
import menu from './zh-TW/menu';
import monitor from './zh-TW/monitor';
import result from './zh-TW/result';
import settingDrawer from './zh-TW/settingDrawer';
import settings from './zh-TW/settings';
import pwa from './zh-TW/pwa';

export default {
  'navBar.lang': '語言',
  'layout.user.link.help': '幫助',
  'layout.user.link.privacy': '隱私',
  'layout.user.link.terms': '條款',
  'app.home.introduce': '介紹',
  'app.forms.basic.title': '基礎表單',
  'app.forms.basic.description':
    '表單頁用於向用戶收集或驗證信息，基礎表單常見於數據項較少的表單場景。',
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
  'menu.housingManagement.visit': '看房請求',
  'menu.housingManagement.rent': '租房管理',
  'menu.housingManagement.list': '房源列表',
  'menu.housingManagement.add': '新增房源',

  'menu.landlord': '房東管理',

  'menu.users': '用戶管理',

  'menu.contract': '合同管理',

  'menu.news': '資訊管理',

  'menu.qa': '問答管理',

  'menu.finance': '財務管理',
  'menu.finance.bill': '賬單管理',
  'menu.finance.withdrawal': '提現審核',

  'menu.system': '系統管理',
  'menu.system.interface': '房源接口',
  'menu.system.dict': '字典配置',
  'menu.system.contract': '合同模板',
};
