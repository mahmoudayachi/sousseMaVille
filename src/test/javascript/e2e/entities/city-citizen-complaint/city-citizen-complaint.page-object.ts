import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed, waitUntilDisplayed, click, waitUntilHidden, isVisible } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

import CityCitizenComplaintUpdatePage from './city-citizen-complaint-update.page-object';

const expect = chai.expect;
export class CityCitizenComplaintDeleteDialog {
  deleteModal = element(by.className('modal'));
  private dialogTitle: ElementFinder = element(by.id('sousseMaVilleApp.cityCitizenComplaint.delete.question'));
  private confirmButton = element(by.id('jhi-confirm-delete-cityCitizenComplaint'));

  getDialogTitle() {
    return this.dialogTitle;
  }

  async clickOnConfirmButton() {
    await this.confirmButton.click();
  }
}

export default class CityCitizenComplaintComponentsPage {
  createButton: ElementFinder = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('div table .btn-danger'));
  title: ElementFinder = element(by.id('city-citizen-complaint-heading'));
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
    await navBarPage.getEntityPage('city-citizen-complaint');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }

  async goToCreateCityCitizenComplaint() {
    await this.createButton.click();
    return new CityCitizenComplaintUpdatePage();
  }

  async deleteCityCitizenComplaint() {
    const deleteButton = this.getDeleteButton(this.records.last());
    await click(deleteButton);

    const cityCitizenComplaintDeleteDialog = new CityCitizenComplaintDeleteDialog();
    await waitUntilDisplayed(cityCitizenComplaintDeleteDialog.deleteModal);
    expect(await cityCitizenComplaintDeleteDialog.getDialogTitle().getAttribute('id')).to.match(
      /sousseMaVilleApp.cityCitizenComplaint.delete.question/
    );
    await cityCitizenComplaintDeleteDialog.clickOnConfirmButton();

    await waitUntilHidden(cityCitizenComplaintDeleteDialog.deleteModal);

    expect(await isVisible(cityCitizenComplaintDeleteDialog.deleteModal)).to.be.false;
  }
}
