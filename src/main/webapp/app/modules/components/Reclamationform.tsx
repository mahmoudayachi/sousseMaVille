import React from 'react';
import { useState } from 'react';
import './Reclamationform.scss';
import { Form, FormGroup, Label, Input, FormText, Button } from 'reactstrap';
import ImageUpload from './Imageuploader';
import axios from 'axios';
import ReclamationCategoryCard from './ReclamationCategoryCard';
import { current } from '@reduxjs/toolkit';
import ComplaintCategory from 'app/entities/complaint-category/complaint-category';

interface FormValues {
  firstname: string;
  lastname: string;
  email: string;
  phonenumber: string;
  description: string;
  date: string;
  address: string;
  sharewithpublic: boolean;
  googlemapsx: string;
  googlemapy: string;
  image: Blob;
}
const initialFormValues: FormValues = {
  firstname: '',
  lastname: '',
  email: '',
  phonenumber: '',
  description: '',
  date: '',
  address: '',
  googlemapsx: '',
  googlemapy: '',
  sharewithpublic: false,
  image: null,
};

const Reclamationform = categorydata => {
  const [formValues, SetFormValues] = useState<FormValues>(initialFormValues);

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = event.target;
    SetFormValues({ ...formValues, [name]: value });
  };

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, checked } = event.target;
    SetFormValues({ ...formValues, [name]: checked });
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    axios
      .post('http://localhost:8080/api/city-citizen-complaints', formValues)
      .then(response => console.log(response))
      .catch(error => console.log(error));
  };

  const submit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    console.log(formValues);
    SetFormValues(initialFormValues);
  };

  return (
    <Form onSubmit={handleSubmit}>
      <FormGroup>
        <FormGroup>
          <ImageUpload />
          <FormGroup>
            <FormGroup>
              <Label className="labels" for="user">
                complaintstate
              </Label>
              <Input type="text" className="input" name="complaintstate" onChange={handleInputChange} placeholder="complaintstate"></Input>
            </FormGroup>
          </FormGroup>
        </FormGroup>
        <FormGroup>
          <Label for="googlemapsx" className="labels">
            googlemapsx
          </Label>
          <Input type="text" name="googlemapsx" placeholder="googlemapsx" onChange={handleInputChange} className="input"></Input>
        </FormGroup>
        <FormGroup>
          <Label for="googlemapy" className="labels">
            googlemapy
          </Label>
          <Input type="text" name="googlemapy" onChange={handleInputChange} className="input" placeholder="googlemapy"></Input>
        </FormGroup>
        <FormGroup>
          <Label for="description" className="labels">
            description
          </Label>
          <Input
            id="description"
            name="description"
            type="textarea"
            value={formValues.description}
            className="textarea"
            aria-rowspan={300}
            required
            onChange={handleInputChange}
          />
        </FormGroup>
        <FormGroup>
          <Label for="adresse" className="labels">
            adresse
          </Label>
          <Input
            id="address"
            name="address"
            required
            placeholder="address"
            type="text"
            value={formValues.address}
            className="input"
            onChange={handleInputChange}
          />
        </FormGroup>

        <FormGroup>
          <Label for="date" className="labels">
            date
          </Label>
          <Input
            id="date"
            name="date"
            value={formValues.date}
            required
            placeholder="date"
            type="date"
            className="input"
            onChange={handleInputChange}
          />
        </FormGroup>
        <FormGroup>
          <div className="container-checkbox">
            <Input type="checkbox" checked={formValues.sharewithpublic} name="sharewithpublic" onChange={handleCheckboxChange} />
            <Label className="check-labels">afficher en public</Label>
          </div>
        </FormGroup>
      </FormGroup>
      <div className="border">
        <FormGroup className="form">
          <span className="span">vos coordonnées</span>
          <FormGroup>
            <Label for="firstname" className="labels">
              Nom
            </Label>
            <Input
              id="firstname"
              name="firstname"
              required
              placeholder="Nom"
              type="text"
              value={formValues.firstname}
              className="input"
              onChange={handleInputChange}
            />
          </FormGroup>

          <FormGroup>
            <Label for="lastname" className="labels">
              Prénom
            </Label>
            <Input
              id="lastname"
              name="lastname"
              required
              value={formValues.lastname}
              placeholder="Prénom"
              type="text"
              className="input"
              onChange={handleInputChange}
            />
          </FormGroup>

          <FormGroup>
            <Label for="email" className="email">
              Email
            </Label>
            <Input
              id="email"
              name="email"
              value={formValues.email}
              placeholder="email"
              type="email"
              className="input"
              onChange={handleInputChange}
            />
          </FormGroup>

          <FormGroup>
            <Label for="numérotel" className="numero">
              Numérotel
            </Label>
            <Input
              id="numérotel"
              value={formValues.phonenumber}
              name="phonenumber"
              placeholder="phonenumber"
              type="number"
              className="input"
              onChange={handleInputChange}
            />
          </FormGroup>

          <Button type="submit" className="button">
            envoyer
          </Button>
        </FormGroup>
      </div>
    </Form>
  );
};

export default Reclamationform;
