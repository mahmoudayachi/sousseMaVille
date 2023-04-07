import React from 'react';
import { useState } from 'react';
import './Reclamationform.scss';
import { Form, FormGroup, Label, Input, FormText, Button } from 'reactstrap';
import ImageUpload from './Imageuploader';

interface Reclamation {
  nom: string;
  prénom: string;
  email: string;
  numerotelephone: string;
  description: string;
  date: string;
  Adresse: String;
}

const Reclamationform = () => {
  const [reclamation, setReclamation] = useState<Reclamation>({
    nom: '',
    prénom: '',
    email: '',
    numerotelephone: '',
    description: '',
    date: '',
    Adresse: '',
  });

  const handleChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setReclamation({
      ...reclamation,
      [event.target.name]: event.target.value,
    });
  };

  return (
    <Form>
      <FormGroup>
        <FormGroup>
          <ImageUpload />
        </FormGroup>
        <FormGroup>
          <Label for="description" className="labels">
            description
          </Label>
          <Input id="description" name="description" type="textarea" className="textarea" aria-rowspan={300} onChange={handleChange} />
        </FormGroup>
        <FormGroup>
          <Label for="adresse" className="labels">
            adresse
          </Label>
          <Input id="adresse" name="adresse" placeholder="adresse" type="text" className="input" onChange={handleChange} />
        </FormGroup>

        <FormGroup>
          <Label for="date" className="labels">
            date
          </Label>
          <Input id="date" name="date" placeholder="date" type="date" className="input" onChange={handleChange} />
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
          <Label for="nom" className="labels">
            Nom
          </Label>
          <Input id="Nom" name="nom" placeholder="Nom" type="text" className="input" onChange={handleChange} />
        </FormGroup>

        <FormGroup>
          <Label for="prénom" className="labels">
            Prénom
          </Label>
          <Input id="prénom" name="prénom" placeholder="Prénom" type="text" className="input" onChange={handleChange} />
        </FormGroup>

        <FormGroup>
          <Label for="email" className="email">
            Email
          </Label>
          <Input id="email" name="email" placeholder="email" type="email" className="input" onChange={handleChange} />
        </FormGroup>

        <FormGroup>
          <Label for="numérotel" className="numero">
            Numérotel
          </Label>
          <Input id="numérotel" name="numérotel" placeholder="numérotel" type="number" className="input" onChange={handleChange} />
        </FormGroup>

        <Button className="button">envoyer</Button>
      </FormGroup>
    </Form>
  );
};

export default Reclamationform;
