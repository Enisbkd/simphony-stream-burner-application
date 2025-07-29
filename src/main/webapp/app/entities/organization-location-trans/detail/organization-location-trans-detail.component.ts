import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IOrganizationLocationTrans } from '../organization-location-trans.model';

@Component({
  selector: 'jhi-organization-location-trans-detail',
  templateUrl: './organization-location-trans-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class OrganizationLocationTransDetailComponent {
  organizationLocationTrans = input<IOrganizationLocationTrans | null>(null);

  previousState(): void {
    window.history.back();
  }
}
