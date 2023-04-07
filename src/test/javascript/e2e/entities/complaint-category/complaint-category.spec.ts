import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import ComplaintCategoryComponentsPage from './complaint-category.page-object';
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

describe('ComplaintCategory e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let complaintCategoryComponentsPage: ComplaintCategoryComponentsPage;
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
    complaintCategoryComponentsPage = new ComplaintCategoryComponentsPage();
    complaintCategoryComponentsPage = await complaintCategoryComponentsPage.goToPage(navBarPage);
  });

  it('should load ComplaintCategories', async () => {
    expect(await complaintCategoryComponentsPage.title.getText()).to.match(/Complaint Categories/);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
