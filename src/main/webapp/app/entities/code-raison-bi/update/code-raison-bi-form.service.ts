import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { ICodeRaisonBI, NewCodeRaisonBI } from '../code-raison-bi.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts ICodeRaisonBI for edit and NewCodeRaisonBIFormGroupInput for create.
 */
type CodeRaisonBIFormGroupInput = ICodeRaisonBI | PartialWithRequiredKeyOf<NewCodeRaisonBI>;

type CodeRaisonBIFormDefaults = Pick<NewCodeRaisonBI, 'id'>;

type CodeRaisonBIFormGroupContent = {
  id: FormControl<ICodeRaisonBI['id'] | NewCodeRaisonBI['id']>;
  nomCourt: FormControl<ICodeRaisonBI['nomCourt']>;
  nomMstr: FormControl<ICodeRaisonBI['nomMstr']>;
  numMstr: FormControl<ICodeRaisonBI['numMstr']>;
  name: FormControl<ICodeRaisonBI['name']>;
  etablissementRef: FormControl<ICodeRaisonBI['etablissementRef']>;
};

export type CodeRaisonBIFormGroup = FormGroup<CodeRaisonBIFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class CodeRaisonBIFormService {
  createCodeRaisonBIFormGroup(codeRaisonBI: CodeRaisonBIFormGroupInput = { id: null }): CodeRaisonBIFormGroup {
    const codeRaisonBIRawValue = {
      ...this.getFormDefaults(),
      ...codeRaisonBI,
    };
    return new FormGroup<CodeRaisonBIFormGroupContent>({
      id: new FormControl(
        { value: codeRaisonBIRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      nomCourt: new FormControl(codeRaisonBIRawValue.nomCourt, {
        validators: [Validators.required],
      }),
      nomMstr: new FormControl(codeRaisonBIRawValue.nomMstr),
      numMstr: new FormControl(codeRaisonBIRawValue.numMstr),
      name: new FormControl(codeRaisonBIRawValue.name),
      etablissementRef: new FormControl(codeRaisonBIRawValue.etablissementRef, {
        validators: [Validators.required],
      }),
    });
  }

  getCodeRaisonBI(form: CodeRaisonBIFormGroup): ICodeRaisonBI | NewCodeRaisonBI {
    return form.getRawValue() as ICodeRaisonBI | NewCodeRaisonBI;
  }

  resetForm(form: CodeRaisonBIFormGroup, codeRaisonBI: CodeRaisonBIFormGroupInput): void {
    const codeRaisonBIRawValue = { ...this.getFormDefaults(), ...codeRaisonBI };
    form.reset(
      {
        ...codeRaisonBIRawValue,
        id: { value: codeRaisonBIRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): CodeRaisonBIFormDefaults {
    return {
      id: null,
    };
  }
}
