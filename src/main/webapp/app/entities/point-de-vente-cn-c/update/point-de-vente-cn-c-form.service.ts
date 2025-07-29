import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPointDeVenteCnC, NewPointDeVenteCnC } from '../point-de-vente-cn-c.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPointDeVenteCnC for edit and NewPointDeVenteCnCFormGroupInput for create.
 */
type PointDeVenteCnCFormGroupInput = IPointDeVenteCnC | PartialWithRequiredKeyOf<NewPointDeVenteCnC>;

type PointDeVenteCnCFormDefaults = Pick<NewPointDeVenteCnC, 'id'>;

type PointDeVenteCnCFormGroupContent = {
  id: FormControl<IPointDeVenteCnC['id'] | NewPointDeVenteCnC['id']>;
  locHierUnitId: FormControl<IPointDeVenteCnC['locHierUnitId']>;
  locObjNum: FormControl<IPointDeVenteCnC['locObjNum']>;
  rvcId: FormControl<IPointDeVenteCnC['rvcId']>;
  kdsControllerId: FormControl<IPointDeVenteCnC['kdsControllerId']>;
  hierUnitId: FormControl<IPointDeVenteCnC['hierUnitId']>;
  objectNum: FormControl<IPointDeVenteCnC['objectNum']>;
  name: FormControl<IPointDeVenteCnC['name']>;
  dataExtensions: FormControl<IPointDeVenteCnC['dataExtensions']>;
};

export type PointDeVenteCnCFormGroup = FormGroup<PointDeVenteCnCFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PointDeVenteCnCFormService {
  createPointDeVenteCnCFormGroup(pointDeVenteCnC: PointDeVenteCnCFormGroupInput = { id: null }): PointDeVenteCnCFormGroup {
    const pointDeVenteCnCRawValue = {
      ...this.getFormDefaults(),
      ...pointDeVenteCnC,
    };
    return new FormGroup<PointDeVenteCnCFormGroupContent>({
      id: new FormControl(
        { value: pointDeVenteCnCRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      locHierUnitId: new FormControl(pointDeVenteCnCRawValue.locHierUnitId),
      locObjNum: new FormControl(pointDeVenteCnCRawValue.locObjNum),
      rvcId: new FormControl(pointDeVenteCnCRawValue.rvcId),
      kdsControllerId: new FormControl(pointDeVenteCnCRawValue.kdsControllerId),
      hierUnitId: new FormControl(pointDeVenteCnCRawValue.hierUnitId),
      objectNum: new FormControl(pointDeVenteCnCRawValue.objectNum),
      name: new FormControl(pointDeVenteCnCRawValue.name),
      dataExtensions: new FormControl(pointDeVenteCnCRawValue.dataExtensions),
    });
  }

  getPointDeVenteCnC(form: PointDeVenteCnCFormGroup): IPointDeVenteCnC | NewPointDeVenteCnC {
    return form.getRawValue() as IPointDeVenteCnC | NewPointDeVenteCnC;
  }

  resetForm(form: PointDeVenteCnCFormGroup, pointDeVenteCnC: PointDeVenteCnCFormGroupInput): void {
    const pointDeVenteCnCRawValue = { ...this.getFormDefaults(), ...pointDeVenteCnC };
    form.reset(
      {
        ...pointDeVenteCnCRawValue,
        id: { value: pointDeVenteCnCRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PointDeVenteCnCFormDefaults {
    return {
      id: null,
    };
  }
}
