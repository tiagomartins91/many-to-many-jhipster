import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IOrganizationProduct } from 'app/shared/model/organization-product.model';

type EntityResponseType = HttpResponse<IOrganizationProduct>;
type EntityArrayResponseType = HttpResponse<IOrganizationProduct[]>;

@Injectable({ providedIn: 'root' })
export class OrganizationProductService {
  public resourceUrl = SERVER_API_URL + 'api/organization-products';

  constructor(protected http: HttpClient) {}

  create(organizationProduct: IOrganizationProduct): Observable<EntityResponseType> {
    return this.http.post<IOrganizationProduct>(this.resourceUrl, organizationProduct, { observe: 'response' });
  }

  update(organizationProduct: IOrganizationProduct): Observable<EntityResponseType> {
    return this.http.put<IOrganizationProduct>(this.resourceUrl, organizationProduct, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IOrganizationProduct>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IOrganizationProduct[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
