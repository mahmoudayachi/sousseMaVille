import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import CityServiceStateUpdatePage from './city-service-state-update.page-object';

const expect = chai.expect;
export class CityServiceStateDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('sousseMaVilleApp.cityServiceState.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-cityServiceState'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class CityServiceStateComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('city-service-state-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  getEditButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-primary.btn-sm'));
  }

  getDeleteButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-danger.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('city-service-state');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateCityServiceState() {
    await this.createButton.click();
    return new CityServiceStateUpdatePage();
  }

  async deleteCityServiceState() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const cityServiceStateDeleteDialog = new CityServiceStateDeleteDialog();
    await waitUntilDisplayed(cityServiceStateDeleteDialog.deleteModal);
    expect(await cityServiceStateDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /sousseMaVilleApp.cityServiceState.delete.question/
    );
    await cityServiceStateDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(cityServiceStateDeleteDialog.deleteModal);

    expect(await isVisible(cityServiceStateDeleteDialog.deleteModal)).to.be.false;
  }
}
