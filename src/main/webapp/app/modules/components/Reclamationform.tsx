import React, { useRef } from 'react';
import { useState, useEffect } from 'react';
import './Reclamationform.scss';
import { Form, FormGroup, Label, Input, FormText, Button } from 'reactstrap';
import ImageUpload from './Imageuploader';
import axios from 'axios';
import { IComplaintCategory } from 'app/shared/model/complaint-category.model';
import { ICityCitizenComplaint } from 'app/shared/model/city-citizen-complaint.model';
import { Complaintstate } from 'app/shared/model/enumerations/complaintstate.model';
import { ICityCitizenPhoto } from 'app/shared/model/city-citizen-photo.model';
import { IUser } from 'app/shared/model/user.model';
import { useAppSelector } from 'app/config/store';
import CityCitizenPhoto from 'app/entities/city-citizen-photo/city-citizen-photo';
import { identity } from 'lodash';

const initialFormValues: ICityCitizenComplaint = {
  firstname: '',
  lastname: '',
  email: '',
  phonenumber: '',
  description: '',
  date: '',
  address: '',
  googlemapsx: '',
  googlemapy: '',
  complaintCategory: {},
  sharewithpublic: false,
  user: {},
  cityCitizenPhotos: [],
  complaintstate: Complaintstate.RECEIVED,
};

const Reclamationform = ({ categorydata }: any) => {
  const [formValues, SetFormValues] = useState<ICityCitizenComplaint>(initialFormValues);
  const [selected, setSelected] = useState({ id: '', name: '' });
  const [array, Setarray] = useState([]);

  const account = useAppSelector(state => state.authentication.account);

  useEffect(() => {
    const category = categorydata.find(u => u.id == selected.id);
    SetFormValues({ ...formValues, [selected.name]: category });
  }, [selected]);

  const add = () => {
    formValues.cityCitizenPhotos = array;
    console.log(formValues.cityCitizenPhotos);
  };

  const setuser = () => {
    SetFormValues({ ...formValues, user: account });
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = event.target;
    SetFormValues({ ...formValues, [name]: value });
  };

  const handleCheckboxChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, checked } = event.target;
    SetFormValues({ ...formValues, [name]: checked });
  };

  const handleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = event.target;
    setSelected({ id: value, name: name });
    add();
    setuser();
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    axios
      .post('http://localhost:8080/api/city-citizen-complaints', formValues)
      .then(response => console.log(response))
      .catch(error => console.log(error));
    SetFormValues({ ...formValues, cityCitizenPhotos: [] });
    console.log(formValues.cityCitizenPhotos);
  };

  const submit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    SetFormValues(initialFormValues);
  };

  return (
    <Form onSubmit={handleSubmit}>
      <div className="border">
        <FormGroup>
          <FormGroup>
            <ImageUpload setarray={Setarray} />
          </FormGroup>
          <FormGroup>
            <Label className="biglabels" for="complaintCategory">
              Categorie
            </Label>
            <Input id="exampleSelect" className="input" name="complaintCategory" onChange={handleChange} type="select">
              {categorydata.map(category => {
                return (
                  <option key={category.id} value={category.id}>
                    {category.name}
                  </option>
                );
              })}
            </Input>
          </FormGroup>
          <FormGroup>
            <FormGroup>
              <Label for="adresse" className="biglabels">
                Addresse
              </Label>
              <Input
                id="address"
                name="address"
                required
                placeholder="addresse"
                type="text"
                className="input"
                onChange={handleInputChange}
              />
            </FormGroup>
            <FormGroup>
              <Label for="googlemapsx" className="biglabels">
                Googlemapsx
              </Label>
              <Input type="text" name="googlemapsx" placeholder="googlemapsx" onChange={handleInputChange} className="input"></Input>
            </FormGroup>
            <FormGroup>
              <Label for="googlemapy" className="biglabels">
                Googlemapy
              </Label>
              <Input type="text" name="googlemapy" onChange={handleInputChange} className="input" placeholder="googlemapy"></Input>
            </FormGroup>
            <FormGroup>
              <Label for="description" className="biglabels">
                Description
              </Label>
              <Input
                id="description"
                name="description"
                type="textarea"
                className="textarea"
                aria-rowspan={300}
                required
                onChange={handleInputChange}
              />
            </FormGroup>

            <FormGroup>
              <Label for="date" className="labels">
                Date
              </Label>
              <Input id="date" name="date" required placeholder="date" type="date" className="input" onChange={handleInputChange} />
            </FormGroup>
          </FormGroup>
          <FormGroup>
            <div className="container-checkbox">
              <Input type="checkbox" name="sharewithpublic" onChange={handleCheckboxChange} />
              <Label className="check-labels">afficher en public</Label>
            </div>
          </FormGroup>
        </FormGroup>
      </div>
      <div className="border">
        <FormGroup className="form">
          <FormGroup>
            <Label for="firstname" className="labels">
              Nom
            </Label>
            <Input id="firstname" name="firstname" placeholder="Nom" type="text" className="input" onChange={handleInputChange} />
          </FormGroup>

          <FormGroup>
            <Label for="lastname" className="labels">
              Prénom
            </Label>
            <Input id="lastname" name="lastname" placeholder="Prénom" type="text" className="input" onChange={handleInputChange} />
          </FormGroup>

          <FormGroup>
            <Label for="email" className="email">
              Email
            </Label>
            <Input id="email" name="email" placeholder="email" type="email" className="input" onChange={handleInputChange} />
          </FormGroup>

          <FormGroup>
            <Label for="numérotel" className="numero">
              Numerotelephone
            </Label>
            <Input
              id="numérotel"
              name="phonenumber"
              placeholder="numerotelephone"
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
