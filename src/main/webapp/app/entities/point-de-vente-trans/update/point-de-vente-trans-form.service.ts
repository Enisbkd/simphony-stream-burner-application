import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IPointDeVenteTrans, NewPointDeVenteTrans } from '../point-de-vente-trans.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPointDeVenteTrans for edit and NewPointDeVenteTransFormGroupInput for create.
 */
type PointDeVenteTransFormGroupInput = IPointDeVenteTrans | PartialWithRequiredKeyOf<NewPointDeVenteTrans>;

type PointDeVenteTransFormDefaults = Pick<NewPointDeVenteTrans, 'id'>;

type PointDeVenteTransFormGroupContent = {
  id: FormControl<IPointDeVenteTrans['id'] | NewPointDeVenteTrans['id']>;
  rvcRef: FormControl<IPointDeVenteTrans['rvcRef']>;
  name: FormControl<IPointDeVenteTrans['name']>;
  locRef: FormControl<IPointDeVenteTrans['locRef']>;
  orgShortName: FormControl<IPointDeVenteTrans['orgShortName']>;
  address: FormControl<IPointDeVenteTrans['address']>;
};

export type PointDeVenteTransFormGroup = FormGroup<PointDeVenteTransFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PointDeVenteTransFormService {
  createPointDeVenteTransFormGroup(pointDeVenteTrans: PointDeVenteTransFormGroupInput = { id: null }): PointDeVenteTransFormGroup {
    const pointDeVenteTransRawValue = {
      ...this.getFormDefaults(),
      ...pointDeVenteTrans,
    };
    return new FormGroup<PointDeVenteTransFormGroupContent>({
      id: new FormControl(
        { value: pointDeVenteTransRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      rvcRef: new FormControl(pointDeVenteTransRawValue.rvcRef),
      name: new FormControl(pointDeVenteTransRawValue.name),
      locRef: new FormControl(pointDeVenteTransRawValue.locRef),
      orgShortName: new FormControl(pointDeVenteTransRawValue.orgShortName),
      address: new FormControl(pointDeVenteTransRawValue.address),
    });
  }

  getPointDeVenteTrans(form: PointDeVenteTransFormGroup): IPointDeVenteTrans | NewPointDeVenteTrans {
    return form.getRawValue() as IPointDeVenteTrans | NewPointDeVenteTrans;
  }

  resetForm(form: PointDeVenteTransFormGroup, pointDeVenteTrans: PointDeVenteTransFormGroupInput): void {
    const pointDeVenteTransRawValue = { ...this.getFormDefaults(), ...pointDeVenteTrans };
    form.reset(
      {
        ...pointDeVenteTransRawValue,
        id: { value: pointDeVenteTransRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PointDeVenteTransFormDefaults {
    return {
      id: null,
    };
  }
}
