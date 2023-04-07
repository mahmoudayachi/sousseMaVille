import { element, by, ElementFinder, ElementArrayFinder } from 'protractor';

import { waitUntilAnyDisplayed } from '../../util/utils';

import NavBarPage from './../../page-objects/navbar-page';

export default class ComplaintCategoryComponentsPage {
  title: ElementFinder = element(by.id('complaint-category-heading'));
  noRecords: ElementFinder = element(by.css('#app-view-container .table-responsive div.alert.alert-warning'));
  table: ElementFinder = element(by.css('#app-view-container div.table-responsive > table'));

  records: ElementArrayFinder = this.table.all(by.css('tbody tr'));

  getDetailsButton(record: ElementFinder) {
    return record.element(by.css('a.btn.btn-info.btn-sm'));
  }

  async goToPage(navBarPage: NavBarPage) {
    await navBarPage.getEntityPage('complaint-category');
    await waitUntilAnyDisplayed([this.noRecords, this.table]);
    return this;
  }
}
