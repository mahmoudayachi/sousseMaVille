import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import UserRoleComponentsPage from './user-role.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';

const expect = chai.expect;

describe('UserRole e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let userRoleComponentsPage: UserRoleComponentsPage;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.waitUntilDisplayed();
    await signInPage.username.sendKeys(username);
    await signInPage.password.sendKeys(password);
    await signInPage.loginButton.click();
    await signInPage.waitUntilHidden();
    await waitUntilDisplayed(navBarPage.entityMenu);
    await waitUntilDisplayed(navBarPage.adminMenu);
    await waitUntilDisplayed(navBarPage.accountMenu);
  });

  beforeEach(async () => {
    await browser.get('/');
    await waitUntilDisplayed(navBarPage.entityMenu);
    userRoleComponentsPage = new UserRoleComponentsPage();
    userRoleComponentsPage = await userRoleComponentsPage.goToPage(navBarPage);
  });

  it('should load UserRoles', async () => {
    expect(await userRoleComponentsPage.title.getText()).to.match(/User Roles/);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
