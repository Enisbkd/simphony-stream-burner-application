import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'bar-code-trans',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.barCodeTrans.home.title' },
    loadChildren: () => import('./bar-code-trans/bar-code-trans.routes'),
  },
  {
    path: 'check-trans',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.checkTrans.home.title' },
    loadChildren: () => import('./check-trans/check-trans.routes'),
  },
  {
    path: 'code-raison-bi',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.codeRaisonBI.home.title' },
    loadChildren: () => import('./code-raison-bi/code-raison-bi.routes'),
  },
  {
    path: 'commission-service-bi',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.commissionServiceBI.home.title' },
    loadChildren: () => import('./commission-service-bi/commission-service-bi.routes'),
  },
  {
    path: 'commission-service-trans',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.commissionServiceTrans.home.title' },
    loadChildren: () => import('./commission-service-trans/commission-service-trans.routes'),
  },
  {
    path: 'detail-line-bi',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.detailLineBI.home.title' },
    loadChildren: () => import('./detail-line-bi/detail-line-bi.routes'),
  },
  {
    path: 'employee-cn-c',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.employeeCnC.home.title' },
    loadChildren: () => import('./employee-cn-c/employee-cn-c.routes'),
  },
  {
    path: 'family-group-cn-c',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.familyGroupCnC.home.title' },
    loadChildren: () => import('./family-group-cn-c/family-group-cn-c.routes'),
  },
  {
    path: 'guest-check-bi',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.guestCheckBI.home.title' },
    loadChildren: () => import('./guest-check-bi/guest-check-bi.routes'),
  },
  {
    path: 'hierarchie-cn-c',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.hierarchieCnC.home.title' },
    loadChildren: () => import('./hierarchie-cn-c/hierarchie-cn-c.routes'),
  },
  {
    path: 'http-call-audit',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.httpCallAudit.home.title' },
    loadChildren: () => import('./http-call-audit/http-call-audit.routes'),
  },
  {
    path: 'location-cn-c',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.locationCnC.home.title' },
    loadChildren: () => import('./location-cn-c/location-cn-c.routes'),
  },
  {
    path: 'major-group-cn-c',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.majorGroupCnC.home.title' },
    loadChildren: () => import('./major-group-cn-c/major-group-cn-c.routes'),
  },
  {
    path: 'menu-item-definitions-cn-c',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.menuItemDefinitionsCnC.home.title' },
    loadChildren: () => import('./menu-item-definitions-cn-c/menu-item-definitions-cn-c.routes'),
  },
  {
    path: 'menu-item-masters-cn-c',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.menuItemMastersCnC.home.title' },
    loadChildren: () => import('./menu-item-masters-cn-c/menu-item-masters-cn-c.routes'),
  },
  {
    path: 'menu-item-prices-cn-c',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.menuItemPricesCnC.home.title' },
    loadChildren: () => import('./menu-item-prices-cn-c/menu-item-prices-cn-c.routes'),
  },
  {
    path: 'mode-paiement-bi',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.modePaiementBI.home.title' },
    loadChildren: () => import('./mode-paiement-bi/mode-paiement-bi.routes'),
  },
  {
    path: 'mode-paiement-trans',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.modePaiementTrans.home.title' },
    loadChildren: () => import('./mode-paiement-trans/mode-paiement-trans.routes'),
  },
  {
    path: 'order-channel-bi',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.orderChannelBI.home.title' },
    loadChildren: () => import('./order-channel-bi/order-channel-bi.routes'),
  },
  {
    path: 'order-type-bi',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.orderTypeBI.home.title' },
    loadChildren: () => import('./order-type-bi/order-type-bi.routes'),
  },
  {
    path: 'organization-location-trans',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.organizationLocationTrans.home.title' },
    loadChildren: () => import('./organization-location-trans/organization-location-trans.routes'),
  },
  {
    path: 'partie-de-journee',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.partieDeJournee.home.title' },
    loadChildren: () => import('./partie-de-journee/partie-de-journee.routes'),
  },
  {
    path: 'point-de-vente-cn-c',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.pointDeVenteCnC.home.title' },
    loadChildren: () => import('./point-de-vente-cn-c/point-de-vente-cn-c.routes'),
  },
  {
    path: 'point-de-vente-trans',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.pointDeVenteTrans.home.title' },
    loadChildren: () => import('./point-de-vente-trans/point-de-vente-trans.routes'),
  },
  {
    path: 'remise-bi',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.remiseBI.home.title' },
    loadChildren: () => import('./remise-bi/remise-bi.routes'),
  },
  {
    path: 'remise-trans',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.remiseTrans.home.title' },
    loadChildren: () => import('./remise-trans/remise-trans.routes'),
  },
  {
    path: 'societe-trans',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.societeTrans.home.title' },
    loadChildren: () => import('./societe-trans/societe-trans.routes'),
  },
  {
    path: 'taxe-bi',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.taxeBI.home.title' },
    loadChildren: () => import('./taxe-bi/taxe-bi.routes'),
  },
  {
    path: 'taxe-class-trans',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.taxeClassTrans.home.title' },
    loadChildren: () => import('./taxe-class-trans/taxe-class-trans.routes'),
  },
  {
    path: 'taxe-rate-trans',
    data: { pageTitle: 'simphonyStreamBurnerApplicationApp.taxeRateTrans.home.title' },
    loadChildren: () => import('./taxe-rate-trans/taxe-rate-trans.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
