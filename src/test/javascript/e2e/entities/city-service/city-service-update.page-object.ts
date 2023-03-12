import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class CityServiceUpdatePage {
  pageTitle: ElementFinder = element(by.id('sousseMaVilleApp.cityService.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  titleInput: ElementFinder = element(by.css('input#city-service-title'));
  descriptionInput: ElementFinder = element(by.css('input#city-service-description'));
  tooltipInput: ElementFinder = element(by.css('input#city-service-tooltip'));
  iconInput: ElementFinder = element(by.css('input#city-service-icon'));
  orderInput: ElementFinder = element(by.css('input#city-service-order'));
  cityservicestateSelect: ElementFinder = element(by.css('select#city-service-cityservicestate'));
  userroleSelect: ElementFinder = element(by.css('select#city-service-userrole'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setTitleInput(title) {
    await this.titleInput.sendKeys(title);
  }

  async getTitleInput() {
    return this.titleInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
  }

  async setTooltipInput(tooltip) {
    await this.tooltipInput.sendKeys(tooltip);
  }

  async getTooltipInput() {
    return this.tooltipInput.getAttribute('value');
  }

  async setIconInput(icon) {
    await this.iconInput.sendKeys(icon);
  }

  async getIconInput() {
    return this.iconInput.getAttribute('value');
  }

  async setOrderInput(order) {
    await this.orderInput.sendKeys(order);
  }

  async getOrderInput() {
    return this.orderInput.getAttribute('value');
  }

  async cityservicestateSelectLastOption() {
    await this.cityservicestateSelect.all(by.tagName('option')).last().click();
  }

  async cityservicestateSelectOption(option) {
    await this.cityservicestateSelect.sendKeys(option);
  }

  getCityservicestateSelect() {
    return this.cityservicestateSelect;
  }

  async getCityservicestateSelectedOption() {
    return this.cityservicestateSelect.element(by.css('option:checked')).getText();
  }

  async userroleSelectLastOption() {
    await this.userroleSelect.all(by.tagName('option')).last().click();
  }

  async userroleSelectOption(option) {
    await this.userroleSelect.sendKeys(option);
  }

  getUserroleSelect() {
    return this.userroleSelect;
  }

  async getUserroleSelectedOption() {
    return this.userroleSelect.element(by.css('option:checked')).getText();
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
    await this.setTitleInput('title');
    await waitUntilDisplayed(this.saveButton);
    await this.setDescriptionInput('description');
    await waitUntilDisplayed(this.saveButton);
    await this.setTooltipInput('tooltip');
    await waitUntilDisplayed(this.saveButton);
    await this.setIconInput('icon');
    await waitUntilDisplayed(this.saveButton);
    await this.setOrderInput('5');
    await this.cityservicestateSelectLastOption();
    // this.userroleSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
