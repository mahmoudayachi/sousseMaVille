import React from 'react';
import { useState } from 'react';
import './Reclamationform.scss';
import { Form, FormGroup, Label, Input, FormText, Button } from 'reactstrap';

interface Reclamation {
  nom: string;
  prénom: string;
  email: string;
  numerotelephone: string;
  subject: string;
  description: string;
}

const Reclamationform = () => {
  const [reclamation, setReclamation] = useState<Reclamation>({
    nom: '',
    prénom: '',
    email: '',
    numerotelephone: '',
    subject: '',
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
        <Label for="adresse">adresse</Label>
        <Input id="adresse" name="adresse" placeholder="adresse" type="text" className="input" />
      </FormGroup>
      <FormGroup>
        <Label for="file">ajouter une photo</Label>
        <Input id="file" name="file" type="file" className="input" />
      </FormGroup>
      <FormGroup>
        <Label for="description">description</Label>
        <Input id="description" name="description" type="textarea" className="textarea" aria-rowspan={300} />
        <FormGroup>
          <Label for="date">date</Label>
          <Input id="date" name="date" placeholder="date" type="date" className="input" />
        </FormGroup>
        <FormGroup check>
          <Input type="checkbox" className="checkbox" />
          <Label check>afficher en public</Label>
        </FormGroup>
      </FormGroup>
      <span className="span">vos coordonnées</span>
      <FormGroup>
        <Label for="nom">Nom</Label>
        <Input id="Nom" name="nom" placeholder="Nom" type="text" className="input" />
      </FormGroup>
      <FormGroup>
        <Label for="prénom">Prénom</Label>
        <Input id="prénom" name="prénom" placeholder="Prénom" type="text" className="input" />
      </FormGroup>
      <FormGroup>
        <Label for="email">EMAIL</Label>
        <Input id="email" name="email" placeholder="email" type="email" className="input" />
      </FormGroup>
      <FormGroup>
        <Label for="numérotel">Numérotel</Label>
        <Input id="numérotel" name="numérotel" placeholder="numérotel" type="number" className="input" />
      </FormGroup>
      <Button className="button">envoyer</Button>
    </Form>
  );
};

export default Reclamationform;
