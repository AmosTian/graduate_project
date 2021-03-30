import analysis from './en-US/analysis';
import exception from './en-US/exception';
import form from './en-US/form';
import globalHeader from './en-US/globalHeader';
import login from './en-US/login';
import menu from './en-US/menu';
import monitor from './en-US/monitor';
import result from './en-US/result';
import settingDrawer from './en-US/settingDrawer';
import settings from './en-US/settings';
import pwa from './en-US/pwa';

export default {
  'navBar.lang': 'Languages',
  'layout.user.link.help': 'Help',
  'layout.user.link.privacy': 'Privacy',
  'layout.user.link.terms': 'Terms',
  'app.home.introduce': 'introduce',
  'app.forms.basic.title': 'Basic form',
  'app.forms.basic.description':
    'Form pages are used to collect or verify information to users, and basic forms are common in scenarios where there are fewer data items.',
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

  'menu.housingManagement': 'Housing Management',
  'menu.housingManagement.visit': 'Visit Request',
  'menu.housingManagement.rent': 'Management',
  'menu.housingManagement.list': 'List',
  'menu.housingManagement.add': 'Add',

  'menu.landlord': 'Landlord Management',

  'menu.users': 'Users Management',

  'menu.contract': 'Contract Management',

  'menu.news': 'News Management',

  'menu.qa': 'Q&A Management',

  'menu.finance': 'Financial Management',
  'menu.finance.bill': 'Bill',
  'menu.finance.withdrawal': 'Withdrawal',

  'menu.system': 'System Management',
  'menu.system.interface': 'Interface',
  'menu.system.dict': 'Dict',
  'menu.system.contract': 'Contract Template',
};
