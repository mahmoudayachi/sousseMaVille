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
import ComplaintCategory from 'app/entities/complaint-category/complaint-category';

const initialFormValues: ICityCitizenComplaint = {
  firstname: '',
  lastname: '',
  email: '',
  phonenumber: '',
  description: '',
  date: '',
  address: '',
  googlemapsx: 'test',
  googlemapy: 'test',
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
  const ref = useRef(null);

  const account = useAppSelector(state => state.authentication.account);

  useEffect(() => {
    const category = categorydata.find(u => u.id == selected.id);
    SetFormValues({ ...formValues, [selected.name]: category });
    console.log(selected);
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
      .then(response => {
        SetFormValues(initialFormValues);
        console.log(response);
        alert('réclamation envoyée');
      })

      .catch(error => console.log(error));
    SetFormValues(initialFormValues);
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
            <Input
              defaultValue={categorydata[0]}
              id="exampleSelect"
              className="input"
              name="complaintCategory"
              onChange={handleChange}
              type="select"
            >
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
                value={formValues.address}
                ref={ref}
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
              <Label for="description" className="biglabels">
                Description
              </Label>
              <Input
                value={formValues.description}
                ref={ref}
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
              <Input
                id="date"
                ref={ref}
                value={formValues.date}
                name="date"
                required
                placeholder="date"
                type="date"
                className="input"
                onChange={handleInputChange}
              />
            </FormGroup>
          </FormGroup>
          <FormGroup>
            <div className="container-checkbox">
              <Input type="checkbox" value={formValues.sharewithpublic} name="sharewithpublic" onChange={handleCheckboxChange} />
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
            <Input
              id="firstname"
              value={(formValues.firstname = account.firstName)}
              ref={ref}
              name="firstname"
              placeholder="Nom"
              type="text"
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
              ref={ref}
              value={(formValues.lastname = account.lastName)}
              name="lastname"
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
              ref={ref}
              name="email"
              value={(formValues.email = account.email)}
              placeholder="email"
              type="email"
              className="input"
              onChange={handleInputChange}
            />
          </FormGroup>

          <FormGroup>
            <Label for="numérotel" className="numero">
              Numéro téléphone
            </Label>
            <Input
              value={formValues.phonenumber}
              ref={ref}
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
