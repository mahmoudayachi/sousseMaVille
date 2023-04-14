import React from 'react';
import './Reclamationhistorycard.scss';

const Reclamationhistorycard = () => {
  return (
    <>
      <div className="history-carte">
        <div className="reclamation-description">Vehicule ventouse</div>
        <div className="adresse">5 rue slayem cheyata</div>
        <div className="image">image</div>
        <div className="state">traité</div>
      </div>
      <div className="history-carte">
        <div className="reclamation-description">Probleme d'eclairage</div>
        <div className="adresse">5 rue slayem cheyata</div>
        <div className="image">image</div>
        <div className="state">traité</div>
      </div>
    </>
  );
};

export default Reclamationhistorycard;
