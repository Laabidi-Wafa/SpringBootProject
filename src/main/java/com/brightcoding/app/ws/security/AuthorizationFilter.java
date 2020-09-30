package com.brightcoding.app.ws.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter{

	public AuthorizationFilter(AuthenticationManager authManager) {
		super(authManager);
		// TODO Auto-generated constructor stub
	}
	
	
	/****************************************************************************************************************************/
	
	
	@Override
	protected void doFilterInternal(HttpServletRequest req,
			HttpServletResponse res, FilterChain chain)
					throws IOException, ServletException 
	{String header = req.getHeader(SecurityConstants.HEADER_STRING);
	
	
		if (header==null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) { //si le header est null ou ne contient pas le token 
			chain.doFilter(req, res); // on ne vous donne pas l'authorisation de récupérer les ressources
			return;
		}
			UsernamePasswordAuthenticationToken authentication = getAuthentication(req);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		chain.doFilter(req, res);
	}

	
	/****************************************************************************************************************************/
	
	
	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(SecurityConstants.HEADER_STRING);
		if(token!=null) {
			token=token.replace(SecurityConstants.TOKEN_PREFIX, "");
			String user = Jwts.parser()
					.setSigningKey(SecurityConstants.TOKEN_SECRET)
					.parseClaimsJws( token )
					.getBody()
					.getSubject();
			
			if (user !=null)
			{
				return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
			}
			return null;
		}
		return null;
	}
	}

