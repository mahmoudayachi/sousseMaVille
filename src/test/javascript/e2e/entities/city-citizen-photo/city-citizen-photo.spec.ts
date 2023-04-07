import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CityCitizenPhotoComponentsPage from './city-citizen-photo.page-object';
import CityCitizenPhotoUpdatePage from './city-citizen-photo-update.page-object';
import {
  waitUntilDisplayed,
  waitUntilAnyDisplayed,
  click,
  getRecordsCount,
  waitUntilHidden,
  waitUntilCount,
  isVisible,
} from '../../util/utils';
import path from 'path';

const expect = chai.expect;

describe('CityCitizenPhoto e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cityCitizenPhotoComponentsPage: CityCitizenPhotoComponentsPage;
  let cityCitizenPhotoUpdatePage: CityCitizenPhotoUpdatePage;
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
    cityCitizenPhotoComponentsPage = new CityCitizenPhotoComponentsPage();
    cityCitizenPhotoComponentsPage = await cityCitizenPhotoComponentsPage.goToPage(navBarPage);
  });

  it('should load CityCitizenPhotos', async () => {
    expect(await cityCitizenPhotoComponentsPage.title.getText()).to.match(/City Citizen Photos/);
    expect(await cityCitizenPhotoComponentsPage.createButton.isEnabled()).to.be.true;
  });

  it('should create and delete CityCitizenPhotos', async () => {
    const beforeRecordsCount = (await isVisible(cityCitizenPhotoComponentsPage.noRecords))
      ? 0
      : await getRecordsCount(cityCitizenPhotoComponentsPage.table);
    cityCitizenPhotoUpdatePage = await cityCitizenPhotoComponentsPage.goToCreateCityCitizenPhoto();
    await cityCitizenPhotoUpdatePage.enterData();
    expect(await isVisible(cityCitizenPhotoUpdatePage.saveButton)).to.be.false;

    expect(await cityCitizenPhotoComponentsPage.createButton.isEnabled()).to.be.true;
    await waitUntilDisplayed(cityCitizenPhotoComponentsPage.table);
    await waitUntilCount(cityCitizenPhotoComponentsPage.records, beforeRecordsCount + 1);
    expect(await cityCitizenPhotoComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

    await cityCitizenPhotoComponentsPage.deleteCityCitizenPhoto();
    if (beforeRecordsCount !== 0) {
      await waitUntilCount(cityCitizenPhotoComponentsPage.records, beforeRecordsCount);
      expect(await cityCitizenPhotoComponentsPage.records.count()).to.eq(beforeRecordsCount);
    } else {
      await waitUntilDisplayed(cityCitizenPhotoComponentsPage.noRecords);
    }
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
