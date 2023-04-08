import { element, by, ElementFinder } from 'protractor';
import { waitUntilDisplayed, waitUntilHidden, isVisible } from '../../util/utils';

const expect = chai.expect;

export default class CityCitizenComplaintUpdatePage {
  pageTitle: ElementFinder = element(by.id('sousseMaVilleApp.cityCitizenComplaint.home.createOrEditLabel'));
  saveButton: ElementFinder = element(by.id('save-entity'));
  cancelButton: ElementFinder = element(by.id('cancel-save'));
  addressInput: ElementFinder = element(by.css('input#city-citizen-complaint-address'));
  descriptionInput: ElementFinder = element(by.css('input#city-citizen-complaint-description'));
  dateInput: ElementFinder = element(by.css('input#city-citizen-complaint-date'));
  sharewithpublicInput: ElementFinder = element(by.css('input#city-citizen-complaint-sharewithpublic'));
  complaintstateSelect: ElementFinder = element(by.css('select#city-citizen-complaint-complaintstate'));
  firstnameInput: ElementFinder = element(by.css('input#city-citizen-complaint-firstname'));
  lastnameInput: ElementFinder = element(by.css('input#city-citizen-complaint-lastname'));
  emailInput: ElementFinder = element(by.css('input#city-citizen-complaint-email'));
  phonenumberInput: ElementFinder = element(by.css('input#city-citizen-complaint-phonenumber'));
  complaintCategorySelect: ElementFinder = element(by.css('select#city-citizen-complaint-complaintCategory'));
  userSelect: ElementFinder = element(by.css('select#city-citizen-complaint-user'));
  cityCitizenPhotoSelect: ElementFinder = element(by.css('select#city-citizen-complaint-cityCitizenPhoto'));

  getPageTitle() {
    return this.pageTitle;
  }

  async setAddressInput(address) {
    await this.addressInput.sendKeys(address);
  }

  async getAddressInput() {
    return this.addressInput.getAttribute('value');
  }

  async setDescriptionInput(description) {
    await this.descriptionInput.sendKeys(description);
  }

  async getDescriptionInput() {
    return this.descriptionInput.getAttribute('value');
  }

  async setDateInput(date) {
    await this.dateInput.sendKeys(date);
  }

  async getDateInput() {
    return this.dateInput.getAttribute('value');
  }

  getSharewithpublicInput() {
    return this.sharewithpublicInput;
  }
  async setComplaintstateSelect(complaintstate) {
    await this.complaintstateSelect.sendKeys(complaintstate);
  }

  async getComplaintstateSelect() {
    return this.complaintstateSelect.element(by.css('option:checked')).getText();
  }

  async complaintstateSelectLastOption() {
    await this.complaintstateSelect.all(by.tagName('option')).last().click();
  }
  async setFirstnameInput(firstname) {
    await this.firstnameInput.sendKeys(firstname);
  }

  async getFirstnameInput() {
    return this.firstnameInput.getAttribute('value');
  }

  async setLastnameInput(lastname) {
    await this.lastnameInput.sendKeys(lastname);
  }

  async getLastnameInput() {
    return this.lastnameInput.getAttribute('value');
  }

  async setEmailInput(email) {
    await this.emailInput.sendKeys(email);
  }

  async getEmailInput() {
    return this.emailInput.getAttribute('value');
  }

  async setPhonenumberInput(phonenumber) {
    await this.phonenumberInput.sendKeys(phonenumber);
  }

  async getPhonenumberInput() {
    return this.phonenumberInput.getAttribute('value');
  }

  async complaintCategorySelectLastOption() {
    await this.complaintCategorySelect.all(by.tagName('option')).last().click();
  }

  async complaintCategorySelectOption(option) {
    await this.complaintCategorySelect.sendKeys(option);
  }

  getComplaintCategorySelect() {
    return this.complaintCategorySelect;
  }

  async getComplaintCategorySelectedOption() {
    return this.complaintCategorySelect.element(by.css('option:checked')).getText();
  }

  async userSelectLastOption() {
    await this.userSelect.all(by.tagName('option')).last().click();
  }

  async userSelectOption(option) {
    await this.userSelect.sendKeys(option);
  }

  getUserSelect() {
    return this.userSelect;
  }

  async getUserSelectedOption() {
    return this.userSelect.element(by.css('option:checked')).getText();
  }

  async cityCitizenPhotoSelectLastOption() {
    await this.cityCitizenPhotoSelect.all(by.tagName('option')).last().click();
  }

  async cityCitizenPhotoSelectOption(option) {
    await this.cityCitizenPhotoSelect.sendKeys(option);
  }

  getCityCitizenPhotoSelect() {
    return this.cityCitizenPhotoSelect;
  }

  async getCityCitizenPhotoSelectedOption() {
    return this.cityCitizenPhotoSelect.element(by.css('option:checked')).getText();
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
    await this.setAddressInput('address');
    await waitUntilDisplayed(this.saveButton);
    await this.setDescriptionInput('description');
    await waitUntilDisplayed(this.saveButton);
    await this.setDateInput('01-01-2001');
    await waitUntilDisplayed(this.saveButton);
    const selectedSharewithpublic = await this.getSharewithpublicInput().isSelected();
    if (selectedSharewithpublic) {
      await this.getSharewithpublicInput().click();
    } else {
      await this.getSharewithpublicInput().click();
    }
    await waitUntilDisplayed(this.saveButton);
    await this.complaintstateSelectLastOption();
    await waitUntilDisplayed(this.saveButton);
    await this.setFirstnameInput('firstname');
    await waitUntilDisplayed(this.saveButton);
    await this.setLastnameInput('lastname');
    await waitUntilDisplayed(this.saveButton);
    await this.setEmailInput('email');
    await waitUntilDisplayed(this.saveButton);
    await this.setPhonenumberInput('phonenumber');
    await this.complaintCategorySelectLastOption();
    await this.userSelectLastOption();
    // this.cityCitizenPhotoSelectLastOption();
    await this.save();
    await waitUntilHidden(this.saveButton);
  }
}
