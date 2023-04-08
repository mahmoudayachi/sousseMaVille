import React from 'react';
import { useState } from 'react';
import './Reclamationform.scss';
import { Form, FormGroup, Label, Input, FormText, Button } from 'reactstrap';
import ImageUpload from './Imageuploader';
import axios from 'axios';

interface Reclamation {
  firstname: string;
  lastname: string;
  email: string;
  phonenumber: string;
  description: string;
  date: string;
  address: string;
}

const Reclamationform = () => {
  const [check, setCheckbox] = useState(false);
  const onclick = () => {
    setCheckbox(true);
  };
  const [reclamation, setReclamation] = useState<Reclamation>({
    firstname: '',
    lastname: '',
    email: '',
    phonenumber: '',
    description: '',
    date: '',
    address: '',
  });

  const handleChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setReclamation({
      ...reclamation,
      [event.target.name]: event.target.value,
    });
  };
  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    axios
      .post('http://localhost:8080//city-citizen-complaints', reclamation)
      .then(response => console.log(response))
      .catch(error => console.log(error));
  };

  const submit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    console.log(reclamation);
  };
  return (
    <Form onSubmit={submit}>
      <FormGroup>
        <FormGroup>
          <ImageUpload />
        </FormGroup>
        <FormGroup>
          <Label for="description" className="labels">
            description
          </Label>
          <Input
            id="description"
            name="description"
            type="textarea"
            value={reclamation.description}
            className="textarea"
            aria-rowspan={300}
            required
            onChange={handleChange}
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
            value={reclamation.address}
            className="input"
            onChange={handleChange}
          />
        </FormGroup>

        <FormGroup>
          <Label for="date" className="labels">
            date
          </Label>
          <Input
            id="date"
            name="date"
            value={reclamation.date}
            required
            placeholder="date"
            type="date"
            className="input"
            onChange={handleChange}
          />
        </FormGroup>
        <FormGroup>
          <div className="container-checkbox">
            <Input type="checkbox" />
            <Label className="check-labels">afficher en public</Label>
          </div>
        </FormGroup>
      </FormGroup>

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
            value={reclamation.firstname}
            className="input"
            onChange={handleChange}
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
            value={reclamation.lastname}
            placeholder="Prénom"
            type="text"
            className="input"
            onChange={handleChange}
          />
        </FormGroup>

        <FormGroup>
          <Label for="email" className="email">
            Email
          </Label>
          <Input
            id="email"
            name="email"
            value={reclamation.email}
            placeholder="email"
            type="email"
            className="input"
            onChange={handleChange}
          />
        </FormGroup>

        <FormGroup>
          <Label for="numérotel" className="numero">
            Numérotel
          </Label>
          <Input
            id="numérotel"
            value={reclamation.phonenumber}
            name="phonenumber"
            placeholder="phonenumber"
            type="number"
            className="input"
            onChange={handleChange}
          />
        </FormGroup>

        <Button className="button">envoyer</Button>
      </FormGroup>
    </Form>
  );
};

export default Reclamationform;
