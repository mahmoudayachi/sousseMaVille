import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CityServiceComponentsPage from './city-service.page-object';
import CityServiceUpdatePage from './city-service-update.page-object';
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

describe('CityService e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cityServiceComponentsPage: CityServiceComponentsPage;
  let cityServiceUpdatePage: CityServiceUpdatePage;
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
    cityServiceComponentsPage = new CityServiceComponentsPage();
    cityServiceComponentsPage = await cityServiceComponentsPage.goToPage(navBarPage);
  });

  it('should load CityServices', async () => {
    expect(await cityServiceComponentsPage.title.getText()).to.match(/City Services/);
    expect(await cityServiceComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete CityServices', async () => {
        const beforeRecordsCount = await isVisible(cityServiceComponentsPage.noRecords) ? 0 : await getRecordsCount(cityServiceComponentsPage.table);
        cityServiceUpdatePage = await cityServiceComponentsPage.goToCreateCityService();
        await cityServiceUpdatePage.enterData();
        expect(await isVisible(cityServiceUpdatePage.saveButton)).to.be.false;

        expect(await cityServiceComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(cityServiceComponentsPage.table);
        await waitUntilCount(cityServiceComponentsPage.records, beforeRecordsCount + 1);
        expect(await cityServiceComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

        await cityServiceComponentsPage.deleteCityService();
        if(beforeRecordsCount !== 0) {
          await waitUntilCount(cityServiceComponentsPage.records, beforeRecordsCount);
          expect(await cityServiceComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(cityServiceComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
