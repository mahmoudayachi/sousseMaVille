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
}

const Reclamationform = () => {
  const [reclamation, setReclamation] = useState<Reclamation>({
    nom: '',
    prénom: '',
    email: '',
    numerotelephone: '',
    description: '',
  });

  const handleChange = (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    setReclamation({
      ...reclamation,
      [event.target.name]: event.target.value,
    });
  };

  return (
    <Form className="form">
      <FormGroup>
        <FormGroup>
          <ImageUpload />
        </FormGroup>
        <FormGroup>
          <Label for="description" className="labels">
            description
          </Label>
          <Input id="description" name="description" type="textarea" className="textarea" aria-rowspan={300} />
        </FormGroup>
        <FormGroup>
          <Label for="adresse" className="labels">
            adresse
          </Label>
          <Input id="adresse" name="adresse" placeholder="adresse" type="text" className="input" />
        </FormGroup>

        <FormGroup>
          <Label for="date" className="labels">
            date
          </Label>
          <Input id="date" name="date" placeholder="date" type="date" className="input" />
        </FormGroup>
        <FormGroup>
          <Input type="checkbox" className="checkbox" />
          <Label className="check">afficher en public</Label>
        </FormGroup>
      </FormGroup>
      <span className="span">vos coordonnées</span>
      <FormGroup>
        <Label for="nom" className="labels">
          Nom
        </Label>
        <Input id="Nom" name="nom" placeholder="Nom" type="text" className="input" />
      </FormGroup>
      <FormGroup>
        <Label for="prénom" className="labels">
          Prénom
        </Label>
        <Input id="prénom" name="prénom" placeholder="Prénom" type="text" className="input" />
      </FormGroup>
      <FormGroup>
        <Label for="email" className="email">
          Email
        </Label>
        <Input id="email" name="email" placeholder="email" type="email" className="input" />
      </FormGroup>
      <FormGroup>
        <Label for="numérotel" className="labels">
          Numérotel
        </Label>
        <Input id="numérotel" name="numérotel" placeholder="numérotel" type="number" className="input" />
      </FormGroup>
      <Button className="button">envoyer</Button>
    </Form>
  );
};

export default Reclamationform;
