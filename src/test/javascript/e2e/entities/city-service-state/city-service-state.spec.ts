import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CityServiceStateComponentsPage from './city-service-state.page-object';
import CityServiceStateUpdatePage from './city-service-state-update.page-object';
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

describe('CityServiceState e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cityServiceStateComponentsPage: CityServiceStateComponentsPage;
  let cityServiceStateUpdatePage: CityServiceStateUpdatePage;
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
    cityServiceStateComponentsPage = new CityServiceStateComponentsPage();
    cityServiceStateComponentsPage = await cityServiceStateComponentsPage.goToPage(navBarPage);
  });

  it('should load CityServiceStates', async () => {
    expect(await cityServiceStateComponentsPage.title.getText()).to.match(/City Service States/);
    expect(await cityServiceStateComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete CityServiceStates', async () => {
    const beforeRecordsCount = (await isVisible(cityServiceStateComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(cityServiceStateComponentsPage.table);
    cityServiceStateUpdatePage = await cityServiceStateComponentsPage.goToCreateCityServiceState();
    await cityServiceStateUpdatePage.enterData();
    expect(await isVisible(cityServiceStateUpdatePage.saveButton)).to.be.false;

    expect(await cityServiceStateComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(cityServiceStateComponentsPage.table);
    await waitUntilCount(cityServiceStateComponentsPage.records, beforeRecordsCount + 1);
    expect(await cityServiceStateComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await cityServiceStateComponentsPage.deleteCityServiceState();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(cityServiceStateComponentsPage.records, beforeRecordsCount);
      expect(await cityServiceStateComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(cityServiceStateComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
