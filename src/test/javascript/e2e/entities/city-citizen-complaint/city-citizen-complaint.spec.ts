import { browser, element, by } from 'protractor';

import NavBarPage from './../../page-objects/navbar-page';
import SignInPage from './../../page-objects/signin-page';
import CityCitizenComplaintComponentsPage from './city-citizen-complaint.page-object';
import CityCitizenComplaintUpdatePage from './city-citizen-complaint-update.page-object';
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

describe('CityCitizenComplaint e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let cityCitizenComplaintComponentsPage: CityCitizenComplaintComponentsPage;
  let cityCitizenComplaintUpdatePage: CityCitizenComplaintUpdatePage;
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
    cityCitizenComplaintComponentsPage = new CityCitizenComplaintComponentsPage();
    cityCitizenComplaintComponentsPage = await cityCitizenComplaintComponentsPage.goToPage(navBarPage);
  });

  it('should load CityCitizenComplaints', async () => {
    expect(await cityCitizenComplaintComponentsPage.title.getText()).to.match(/City Citizen Complaints/);
    expect(await cityCitizenComplaintComponentsPage.createButton.isEnabled()).to.be.true;
  });

  /* it('should create and delete CityCitizenComplaints', async () => {
        const beforeRecordsCount = await isVisible(cityCitizenComplaintComponentsPage.noRecords) ? 0 : await getRecordsCount(cityCitizenComplaintComponentsPage.table);
        cityCitizenComplaintUpdatePage = await cityCitizenComplaintComponentsPage.goToCreateCityCitizenComplaint();
        await cityCitizenComplaintUpdatePage.enterData();
        expect(await isVisible(cityCitizenComplaintUpdatePage.saveButton)).to.be.false;

        expect(await cityCitizenComplaintComponentsPage.createButton.isEnabled()).to.be.true;
        await waitUntilDisplayed(cityCitizenComplaintComponentsPage.table);
        await waitUntilCount(cityCitizenComplaintComponentsPage.records, beforeRecordsCount + 1);
        expect(await cityCitizenComplaintComponentsPage.records.count()).to.eq(beforeRecordsCount + 1);

        await cityCitizenComplaintComponentsPage.deleteCityCitizenComplaint();
        if(beforeRecordsCount !== 0) {
          await waitUntilCount(cityCitizenComplaintComponentsPage.records, beforeRecordsCount);
          expect(await cityCitizenComplaintComponentsPage.records.count()).to.eq(beforeRecordsCount);
        } else {
          await waitUntilDisplayed(cityCitizenComplaintComponentsPage.noRecords);
        }
    }); */

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
