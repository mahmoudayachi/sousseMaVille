import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class CityServiceStateUpdatePage {
  pageTitle: ElementFinder = element(by.id('sousseMaVilleApp.cityServiceState.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  nameSelect: ElementFinder = element(by.css('select#city-service-state-name'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setNameSelect(name) {
    await this.nameSelect.sendKeys(name);
  }

  async getNameSelect() {
    return this.nameSelect.element(by.css('option:checked')).getText();
  }

  async nameSelectLastOption() {
    await this.nameSelect.all(by.tagName('option')).last().click();
  }
  async save() {
    await this.saveButton.click();
  }

  async cancel() {
    await this.cancelButton.click();
  }

  getSaveButton() {
    return this.saveButton;
  }

  async enterData() {
    await waitUntilDisplayed(this.saveButton);
    await this.nameSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
