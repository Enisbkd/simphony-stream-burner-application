import { ICodeRaisonBI, NewCodeRaisonBI } from './code-raison-bi.model';

export const sampleWithRequiredData: ICodeRaisonBI = {
  id: 25364,
  nomCourt: 22325,
  etablissementRef: 'unfit source showboat',
};

export const sampleWithPartialData: ICodeRaisonBI = {
  id: 8433,
  nomCourt: 20715,
  numMstr: 15325,
  etablissementRef: 'pro likable',
};

export const sampleWithFullData: ICodeRaisonBI = {
  id: 10347,
  nomCourt: 2287,
  nomMstr: 'cheese nervously',
  numMstr: 17044,
  name: 'brr tune-up generously',
  etablissementRef: 'interior joyously',
};

export const sampleWithNewData: NewCodeRaisonBI = {
  nomCourt: 21119,
  etablissementRef: 'above',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
